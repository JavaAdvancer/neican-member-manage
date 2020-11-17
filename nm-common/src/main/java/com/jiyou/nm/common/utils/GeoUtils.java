package com.jiyou.nm.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/14.
 * Dis:百度地图反向地址解析工具类
 */

public class GeoUtils {

    public static void main(String []args){
        String lon = "119.07932";
        String lat = "36.778901";
        try {
            String convert = convert(lat,lon);
            System.out.println(convert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *  将经纬度反向地址解析为省市区县的地址
     * */
    public static String regeo(String lat,String lon) throws Exception {
        String result = "";
        URL localURL = new URL("http://restapi.amap.com/v3/geocode/regeo?output=json&location="+lon+","+lat+"&key=8df70b762826f4cdd981f0ed5c0b2745&radius=0&extensions=all");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "utf-8");
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        if (httpURLConnection.getResponseCode() < 300) {
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            //解析数据部分
           result = resultBuffer.toString();
        }
        return result;
    }

    /**
     * 将GPS转换为高德地图坐标
     * */
    public static String convert(String lat,String lon) throws Exception {
        String result = "";
        URL localURL = new URL("http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+lon+","+lat+"&coordsys=gps&output=json&key=8df70b762826f4cdd981f0ed5c0b2745");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "utf-8");
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        if (httpURLConnection.getResponseCode() < 300) {
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            //解析数据部分
            result = resultBuffer.toString();
        }
        return result;
    }
}
