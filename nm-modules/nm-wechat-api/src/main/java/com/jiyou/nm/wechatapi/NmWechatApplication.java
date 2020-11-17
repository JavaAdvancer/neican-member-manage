package com.jiyou.nm.wechatapi;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.jiyou.nm.mapper")
public class NmWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmWechatApplication.class, args);
        System.out.println("================= nm-wechat-api 启动成功 ================");
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
        scannerConfigurer.setBasePackage("com.jiyou.nm.mapper");
        return scannerConfigurer;
    }
}
