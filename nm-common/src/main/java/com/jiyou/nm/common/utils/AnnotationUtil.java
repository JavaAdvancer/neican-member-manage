package com.jiyou.nm.common.utils;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jiyou.nm.common.annotation.*;
import com.jiyou.nm.common.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by pc on 14-6-12.
 */
public class AnnotationUtil {

    public static ExcelInfo getExcelInfo(List list, int groupNumber) {
        ExcelInfo e = new ExcelInfo();

        if (list == null || list.isEmpty()) {
            return null;
        }

        //文件名称
        String execelFileName = "";
//        String execelextension = "" ;
        //标题list
        List<ExcelTitleInfo> titlesList = Lists.newArrayList();
        List<String> fieldNameList = Lists.newArrayList();

        Object object = list.get(0);
//        System.out.println(object.getClass());
//        System.out.println(Reflections.getUserClass(object.getClass()));
        Class clazz = Reflections.getUserClass(object.getClass());


        List<SheetInfo> sheetInfos = null;
        List<LevelTitle> levelTitles = null;
        List<Excels> excelsList = null;
        List<Sheets> sheetsList = null;
        /**
         * 获取类上的Annotation
         */
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            //判断是否包含Execl或Excels标签
            if (annotation.annotationType().equals(Excel.class) || annotation.annotationType().equals(Excels.class)) {
                Excel excel = null;
                if (annotation.annotationType().equals(Excel.class)) {
                    excel = (Excel) annotation;
                } else if (annotation.annotationType().equals(Excels.class)) {
                    Excels excels = (Excels) annotation;
                    Excel[] array = excels.value();
                    for (int i = 0; i < array.length; i++) {
                        if (groupNumber == array[i].groupNumber()) {
                            excel = array[i];
                        }
                    }
                }
                //Excel excel = (Excel) annotation;
                execelFileName = excel.fileName();
//                execelextension = excel.fileextension();

            } else if (annotation.annotationType().equals(Sheet.class) || annotation.annotationType().equals(Sheets.class)) {
                if (sheetInfos == null) {
                    sheetInfos = Lists.newArrayList();
                }
                Sheet sheet = null;

                if (annotation.annotationType().equals(Sheet.class)) {
                    sheet = (Sheet) annotation;
                } else if (annotation.annotationType().equals(Sheets.class)) {
                    Sheets sheets = (Sheets) annotation;
                    Sheet[] array = sheets.value();
                    for (int i = 0; i < array.length; i++) {
                        if (groupNumber == array[i].groupNumber()) {
                            sheet = array[i];
                        }
                    }
                }
                //Sheet sheet = (Sheet)annotation;
                int[] numOfSheet = sheet.numOfSheet();
                int numPerSheet = sheet.numPerSheet();
                String[] sheetNames = sheet.sheetNames();

                if (sheetNames.length == numOfSheet.length && numOfSheet.length != 0) {
                    for (int i = 0; i < numOfSheet.length; i++) {
                        if (numOfSheet[i] <= 0) {
                            numOfSheet[i] = numPerSheet;
                        }
                    }
                } else if (sheetNames.length > numOfSheet.length && numOfSheet.length != 0) {
                    int[] nums = new int[sheetNames.length];
                    System.arraycopy(numOfSheet, 0, nums, 0, numOfSheet.length);
                    for (int i = 0; i < nums.length; i++) {
                        if (nums[i] <= 0) {
                            nums[i] = numPerSheet;
                        }
                    }
                    numOfSheet = nums;
                } else if (numOfSheet.length == 0 && sheetNames.length > 1) {
                    int[] nums = new int[sheetNames.length];
                    for (int i = 0; i < nums.length; i++) {
                        nums[i] = numPerSheet;
                    }
                    numOfSheet = nums;
                } else if (sheetNames.length == 1) {
                    //如果只设置了一个sheet名字, 代表根据 总数据条数\每页个数  个sheet
                    int sheetSize = list.size() / numPerSheet + 1;
                    String sheetName = sheetNames[0];
                    String[] sheetnametemp = new String[sheetSize];
                    for (int i = 0; i < sheetSize; i++) {
                        if (sheetSize == 1) {
                            sheetnametemp[i] = sheetName;
                        } else {
                            sheetnametemp[i] = sheetName + i;
                        }

                    }
                    sheetNames = sheetnametemp;
                    int[] nums = new int[sheetNames.length];
                    for (int i = 0; i < nums.length; i++) {
                        nums[i] = numPerSheet;
                    }
                    numOfSheet = nums;
                }

                for (int i = 0; i < sheetNames.length; i++) {
                    String sheetName = sheetNames[i];
                    int num = numOfSheet[i];
                    SheetInfo sheetInfo = new SheetInfo();
                    sheetInfo.setNum(num);
                    sheetInfo.setSheetName(sheetName);
                    sheetInfos.add(sheetInfo);
                }
            } else if (annotation.annotationType().equals(LevelExcelTitles.class)) {
                if (levelTitles == null) {
                    levelTitles = Lists.newArrayList();
                }
                LevelExcelTitles titles = (LevelExcelTitles) annotation;
                LevelExcelTitle[] array = titles.value();
                for (int i = 0; i < array.length; i++) {
                    //只取分组一样的标题
                    if (groupNumber == array[i].groupNumber()) {
                        int[] rowSpans = array[i].rowSpans();
                        int[] colSpans = array[i].colSpans();
                        int titleLevel = array[i].titleLevel();
                        String[] titlesNames = array[i].titleNames();
                        LevelTitle levelTitle = new LevelTitle();
                        levelTitle.setRowSpans(rowSpans);
                        levelTitle.setColSpans(colSpans);
                        levelTitle.setTitleLevel(titleLevel);
                        levelTitle.setTitleNames(titlesNames);
                        levelTitle.setAligns(array[i].aligns());
                        levelTitles.add(levelTitle);
                    }
                }
            }
        }


        e.setSheetInfos(sheetInfos);

        if (levelTitles != null) {
            Collections.sort(levelTitles);
        }
        e.setLevelTitles(levelTitles);

        if (Strings.isNullOrEmpty(execelFileName)) {
            //不能为空这里
            return null;
        }
        //最终文件名称
//        String execelFullName = Files.getNameWithoutExtension(execelFileName)+"."+ execelextension;
        e.setFileName(execelFileName);


        /**
         * 获取属性上面的Annotation
         */
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
//           System.out.println("fieldName:"+fieldName);
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                //判断是否包含ExcelTitle或ExcelTitles标签(两者只能有一个)
                if (annotation.annotationType().equals(ExcelTitle.class) || annotation.annotationType().equals(ExcelTitles.class)) {
                    //解析标题
                    ExcelTitle excelTitle = null;
                    if (annotation.annotationType().equals(ExcelTitle.class)) {
                        //excelTitle = (ExcelTitle) annotation;
                        //编号一样的才赋值
                        ExcelTitle array = (ExcelTitle) annotation;
                        if (groupNumber == array.groupNumber()) {
                            excelTitle = array;
                        }
                    } else if (annotation.annotationType().equals(ExcelTitles.class)) {
                        ExcelTitles excelTitles = (ExcelTitles) annotation;
                        ExcelTitle[] array = excelTitles.value();
                        for (int i = 0; i < array.length; i++) {
                            if (groupNumber == array[i].groupNumber()) {
                                excelTitle = array[i];
                            }
                        }
                    }
                    //ExcelTitle excelTitle = (ExcelTitle) annotation;
                    if (excelTitle != null) {
                        ExcelTitleInfo info = new ExcelTitleInfo();
                        String titleName = excelTitle.titleName();
                        int width = excelTitle.width();
                        int index = excelTitle.index();
                        int rowSpan = excelTitle.rowSpan();
                        String type = excelTitle.type();
                        String format = excelTitle.format();
                        info.setIndex(index);
                        info.setWidth(width);
                        info.setType(type);
                        info.setFormat(format);
                        info.setRowSpan(rowSpan);
                        info.setGroupNumber(excelTitle.groupNumber());
                        //校验titile的名称，如果有插入list，如果没有用field名称
                        if (Strings.isNullOrEmpty(titleName)) {
                            info.setTitle(fieldName);
                            titlesList.add(info);
                        } else {
                            info.setTitle(titleName);
                            titlesList.add(info);
                        }
                        info.setFieldName(fieldName);
                        fieldNameList.add(fieldName);
                    }

                }
            }
        }

//        System.out.println(titlesList.toString());
        if ((!titlesList.isEmpty()) && titlesList.size() != 1) {
            //Lists.
            Collections.sort(titlesList);
        }
        e.setTitleInfo(titlesList);
//        System.out.println(titlesList.toString());

        //getAccessibleField Reflections.getFieldValue()
        List<List<ExcelBodyInfo>> result = Lists.newArrayList();
        for (Object o : list) {
            List<ExcelBodyInfo> row = Lists.newArrayList();
            for (ExcelTitleInfo excelTitleInfo : titlesList) {
                Object value = Reflections.getFieldValue(o, excelTitleInfo.getFieldName());
                ExcelBodyInfo excelBodyInfo = new ExcelBodyInfo();
                excelBodyInfo.setFormat(excelTitleInfo.getFormat());
                if (value == null) {
                    excelBodyInfo.setValue("");
                    excelBodyInfo.setType("String");
                    row.add(excelBodyInfo);
                } else if (value instanceof Date) {
                    String format = "yyyy/MM/dd";
                    if(StringUtils.isNoneBlank(excelTitleInfo.getFormat())){
                        format = excelTitleInfo.getFormat();
                    }
                    //时间类型的字段导出, 强制转换成时间格式
                    excelBodyInfo.setValue(TimeUtils.getDateStringByForm((Date) value, format));
                    excelBodyInfo.setType("Date");
                    row.add(excelBodyInfo);
                } else if (value instanceof BigDecimal) {
                    //BigDecimal格式的数据强制转换数字格式,保留两位小数
                    excelBodyInfo.setValue(value.toString());
                    excelBodyInfo.setType("BigDecimal");
                    row.add(excelBodyInfo);
                } else if (value instanceof Integer) {
                    //Integer类型强制转成数字类型
                    excelBodyInfo.setValue(value.toString());
                    excelBodyInfo.setType("Integer");
                    row.add(excelBodyInfo);
                }else if (value instanceof Double) {
                    //Integer类型强制转成数字类型
                    excelBodyInfo.setValue(value.toString());
                    excelBodyInfo.setType("Double");
                    row.add(excelBodyInfo);
                } else {
                    //String类型,根据注解的type进行转换类型
                    excelBodyInfo.setValue(value.toString());
                    excelBodyInfo.setType(excelTitleInfo.getType());
                    row.add(excelBodyInfo);
                }

            }
            result.add(row);

        }
        e.setBodyInfo(result);

        return e;
    }

    public static List<Map> transferDynamicExcelToList(ExcelInfo excelInfo) {
        List<String> excelTitle = excelInfo.getTitle();
        List list = new ArrayList();
        Map map = null;
        for (List<ExcelBodyInfo> row : excelInfo.getBodyInfo()) {
            map = new HashMap();
            for (int i = 0; i < excelTitle.size(); i++) {
                if (0 == row.size()) {
                    continue;
                }
                String value = row.get(i).getValue();
                map.put(excelTitle.get(i), value);
            }

            list.add(map);
        }

        return list;
    }

    public static <T> List<T> transferExcelToList(ExcelInfo excelInfo, Class<T> clazz) throws Exception {

        List<String> needTitle = new ArrayList<String>();
        Map<String, Field> map = new HashMap<String, Field>();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
//            System.out.println("fieldName:"+fieldName);
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                //判断是否包含ExcelTitle标签
                if (annotation.annotationType().equals(ExcelTitle.class)) {
                    ExcelTitle excelTitle = (ExcelTitle) annotation;
                    String titlename = excelTitle.titleName();

                    //校验titile的名称，如果有插入list，如果没有用field名称
                    if (Strings.isNullOrEmpty(titlename)) {
                        needTitle.add(fieldName);
                    } else {
                        needTitle.add(titlename);
                    }
                    map.put(titlename, field);

                }
            }
        }

        List<String> excelTitle = excelInfo.getTitle();
        List<Integer> indexList = new ArrayList<Integer>();
        for (String s : needTitle) {
            int index = excelTitle.indexOf(s);
            indexList.add(index);
        }

        List<T> list = new ArrayList<T>();

        for (List<ExcelBodyInfo> row : excelInfo.getBodyInfo()) {
            Object o = null;
            try {
                o = clazz.newInstance();
            } catch (InstantiationException e) {
                //e.printStackTrace();
                //log.error("exception",e);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
                //log.error("exception",e);
            }
            if (row.size() < needTitle.size()) {
                break;
            }
            for (int i = 0; i < needTitle.size(); i++) {
                String t = needTitle.get(i);
                Field f = map.get(t);
                int index = indexList.get(i);
                if (index == -1) {
                    break;
                }
                String value = row.get(index).getValue();
                if (value == null || "".equals(value)) {
                    continue;
                }
                //读取Excel表格中的数据都是String类型的，放入实体类字段有可能不匹配需要转型
                if (f.getType().equals(String.class)) {
                    Reflections.setFieldValue(o, f.getName(), value);
                } else if (f.getType().equals(Integer.class)) {
                    Reflections.setFieldValue(o, f.getName(), Integer.parseInt(value));
                } else if (f.getType().equals(Date.class)) {
                    if (value.length() > 11) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Reflections.setFieldValue(o, f.getName(), format.parse(value));
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Reflections.setFieldValue(o, f.getName(), format.parse(value));
                    }
                } else if (f.getType().equals(Long.class)) {
                    Reflections.setFieldValue(o, f.getName(), Long.parseLong(value));
                } else if (f.getType().equals(Double.class)) {
                    Reflections.setFieldValue(o, f.getName(), Double.parseDouble(value));
                } else if (f.getType().equals(Float.class)) {
                    Reflections.setFieldValue(o, f.getName(), Float.parseFloat(value));
                }
            }
            list.add((T) o);
        }
        return list;
    }

}
