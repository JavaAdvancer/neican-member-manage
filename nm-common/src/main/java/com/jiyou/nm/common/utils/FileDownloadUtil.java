package com.jiyou.nm.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Arrays;

public class FileDownloadUtil {

    public static void httpDownload(Path filePath) throws IOException {
        File file = filePath.toFile();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //Excel下载的头
        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");//原来是 application/x-download
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Vary", "Origin");
        String displayFilename = filePath.getFileName().toString();
        if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")
                || StringUtils.contains(userAgent, "Edge")) {//IE浏览器
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
            displayFilename = new String(displayFilename.getBytes(), "ISO8859-1");
        } else {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");//其他浏览器
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file), 4096);
        IOUtils.copy(in, response.getOutputStream());
        response.flushBuffer();
    }

    public static HttpHeaders buildDowFileHttpHeaders(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        //设置httpHeaders,使浏览器响应下载
        HttpHeaders headers = new HttpHeaders();
        String userAgent = request.getHeader("User-Agent");
        headers.setPragma("No-cache");
        headers.setCacheControl("must-revalidate, no-transform");
        headers.setExpires(0L);
        //headers.setAccessControlAllowOrigin("*");
        headers.setAccessControlAllowCredentials(true);
        headers.set("Access-Control-Allow-Headers", "*");
        headers.setVary(Lists.newArrayList("Origin"));
        if (Arrays.asList("MSIE", "Trident", "Edge").contains(userAgent)) {
            //IE浏览器
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            ////google,火狐浏览器
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        headers.set("Content-Disposition", "attachment;filename=" + fileName);
        //设置响应方式为二进制，以二进制流传输
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }


}
