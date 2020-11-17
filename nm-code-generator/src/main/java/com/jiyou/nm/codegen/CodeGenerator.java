package com.jiyou.nm.codegen;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     *是否生成controller or service 输入
     * @param type 1-controller 2-service
     * @return
     */
    public static boolean scannerControllerOrService(Integer type) {
        boolean rs = false;
        //String tip = type==1?"controller":"service";
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("是否生成controller和service (Y生成,其他不生成）：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt) && "Y".equals(ipt)) {
                rs = true;
            }
        }else{
            rs = false;
        }
        return rs;
    }

    public static String[] scannerControllerOrServiceModules(Integer type) {
        String tip = type==1?"controller":"service";
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入controler和service所在模块（admin或者wechat）：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)
                    && ("admin".equals(ipt) || "wechat".equals(ipt))) {
                if("admin".equals(ipt)){
                    return new String[]{"nm-admin-api","adminapi"};
                }else{
                    return new String[]{"nm-wechat-api","wechatapi"};
                }
            }else{
                throw new MybatisPlusException("请输入正确的" + tip + "所在模块名称！");
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "所在模块名称！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String daoInterfacePath = projectPath+"/nm-dao/src/main/java/com/jiyou/nm/mapper";
        String daoXmlPath = projectPath+"/nm-dao/src/main/resources/mappers/";
        String entityPath = projectPath+"/nm-model/src/main/java/com/jiyou/nm/model/entity";
        String controllerPathTemplate =projectPath+"/nm-modules/%s/src/main/java/com/jiyou/nm/%s/controller";
        String servicePathTemplate =projectPath+"/nm-modules/%s/src/main/java/com/jiyou/nm/%s/service";
        String serviceImplPathTemplate =projectPath+"/nm-modules/%s/src/main/java/com/jiyou/nm/%s/service/impl";

        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("");
        gc.setOpen(false);
        gc.setSwagger2(false);// 实体属性 Swagger2 注解
        gc.setDateType(DateType.ONLY_DATE);
        gc.setIdType(IdType.NONE);
        gc.setActiveRecord(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://39.106.0.169:3306/neican_member?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("neican");
        dsc.setPassword("neican@)20");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.jiyou.nm");
        pc.setController("");
        pc.setService("");
        pc.setMapper("mapper");
        pc.setEntity("model.entity");
        Map pathInfo = new HashMap<>();
        pathInfo.put(ConstVal.ENTITY_PATH,entityPath);
        pathInfo.put(ConstVal.MAPPER_PATH, daoInterfacePath);
        pathInfo.put(ConstVal.XML_PATH, daoXmlPath);
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
////                return projectPath + "/src/main/resources/mybatis/mapper/" + pc.getModuleName()
////                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//                return projectPath + "/src/main/resources/mybatis/mappers/" + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

       // templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
       // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //是否生成service和controller
        boolean genController = scannerControllerOrService(1);
        if(genController){
            String[] modluesAry = scannerControllerOrServiceModules(1);
            pc.setController(modluesAry[1]+".controller");
            pathInfo.put(ConstVal.CONTROLLER_PATH, String.format(controllerPathTemplate,modluesAry));
            pc.setService(modluesAry[1]+".service");
            pc.setServiceImpl(modluesAry[1]+".service.impl");
            pathInfo.put(ConstVal.SERVICE_PATH, String.format(servicePathTemplate,modluesAry));
            pathInfo.put(ConstVal.SERVICE_IMPL_PATH, String.format(serviceImplPathTemplate,modluesAry));
        }
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}