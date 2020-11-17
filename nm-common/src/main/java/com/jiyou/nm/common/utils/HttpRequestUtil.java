package com.jiyou.nm.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpRequestUtil {


    public static String getJSONStrParam(HttpServletRequest request){
        String jsonStr = "";
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            // 直接将json信息打印出来
            jsonStr = sb.toString();
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
