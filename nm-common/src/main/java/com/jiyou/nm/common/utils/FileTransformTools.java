package com.jiyou.nm.common.utils;


import com.jiyou.nm.common.entity.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 *
 * @author cmw
 * @version 1.0
 * 2018/4/23 16:13
 **/
public class FileTransformTools {

    /**
     * 写文件到客户端
     *
     * @param fileItem
     * @param request
     * @param response
     */
    public static void transformToClient(FileItem fileItem, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Excel下载的头
        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");
        String displayFilename = fileItem.getFileName().substring(fileItem.getFileName().lastIndexOf('_') + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        response.setHeader("Content-Length", String.valueOf(fileItem.getContent().length));
        try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            out.write(fileItem.getContent());
        }
    }
}
