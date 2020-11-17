package com.jiyou.nm.common.excel;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ExcelUtil {


    public static void exportExcel(List<?> list, String fileName, String title, String sheetName, Class<?> pojoClass) throws IOException {
        ExportParams exportParams = new ExportParams(null, sheetName);
        exportParams.setType(ExcelType.XSSF);
        exportParams.setCreateHeadRows(true);
        exportParams.setStyle(ExcelStyleUtil.class);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        defaultExport(list, pojoClass, fileName, exportParams, servletRequestAttributes.getResponse(), servletRequestAttributes.getRequest());

    }

    public static void exportExcel(List<?> list, String fileName, String title, String sheetName, Class<?> pojoClass, boolean isCreateHeader, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, exportParams, response, request);

    }

    public static void exportExcel(List<?> list, String fileName, String title, String sheetName, Class<?> pojoClass, HttpServletResponse response, HttpServletRequest request) throws IOException {
        defaultExport(list, pojoClass, fileName, new ExportParams(title, sheetName), response, request);
    }

    /*动态*/
    public static void exportExcel(List<Map<String, Object>> list, List<ExcelExportEntity> colList, String fileName, String title, String sheetName) throws IOException {
        ExportParams exportParams = new ExportParams(null, sheetName);
        exportParams.setType(ExcelType.XSSF);
        exportParams.setCreateHeadRows(true);
        exportParams.setStyle(ExcelStyleUtil.class);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, colList, list);
        if (workbook != null) {
            downLoadExcel(workbook, fileName, servletRequestAttributes.getRequest(), servletRequestAttributes.getResponse());
        }
    }


    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response, HttpServletRequest request) {
        defaultExport(list, fileName, response, request);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(workbook, fileName, request, response);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response, HttpServletRequest request) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * 写文件到客户端
     *
     * @param workbook
     * @param fileName
     * @param request
     * @param response
     */
    public static void downLoadExcel(Workbook workbook, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Excel下载的头
        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/vnd.ms-excel");//原来是 application/x-download
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers",  "*");
        response.setHeader("Vary", "Origin");
        String displayFilename = fileName.substring(fileName.lastIndexOf('_') + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent,"Trident")
                || StringUtils.contains(userAgent,"Edge")){//IE浏览器
            displayFilename = URLEncoder.encode(displayFilename,"UTF-8");
        }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
            displayFilename = new String(displayFilename.getBytes(), "ISO8859-1");
        }else{
            displayFilename = URLEncoder.encode(displayFilename,"UTF-8");//其他浏览器
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        /*if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename =displayFilename;// new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }*/
        //response.setHeader("Content-Length", String.valueOf(workbook.getContent().length));
        workbook.write(response.getOutputStream());
    }


    public static ServletOutputStream getOutPutStream(String fileName) throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse resp = servletRequestAttributes.getResponse();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
        resp.reset();
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "must-revalidate, no-transform");
        resp.setDateHeader("Expires", 0L);
        resp.setContentType("application/vnd.ms-excel");//原来是 application/x-download
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Headers",  "*");
        resp.setHeader("Vary", "Origin");
        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent,"Trident")
                || StringUtils.contains(userAgent,"Edge")){//IE浏览器
            fileName = URLEncoder.encode(fileName,"UTF-8");
        }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        }else{
            fileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
        }
        resp.addHeader("Content-Disposition", "attachment;filename="+fileName+ ".xlsx");
        return resp.getOutputStream();
    }
}
