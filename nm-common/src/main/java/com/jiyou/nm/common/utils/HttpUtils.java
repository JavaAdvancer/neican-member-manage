package com.jiyou.nm.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiang on 2018/10/19.
 */
public class HttpUtils {

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return null;
    }


    /**
     * 发送PUT请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPut(String url, Map<String, String> parameters) {
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sBuilder = null;
        UrlEncodedFormEntity entity = null;
        try {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut(url);
            entity = new UrlEncodedFormEntity(list,"UTF-8");
            httpPut.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPut);
            //连接成功
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                br = new BufferedReader(new InputStreamReader(is));
                String tempStr;
                sBuilder = new StringBuilder();
                while ((tempStr = br.readLine()) != null) {
                    sBuilder.append(tempStr);
                }
                br.close();
                is.close();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return sBuilder == null ? "" : sBuilder.toString();
    }

    private static final int TIMEOUT_IN_MILLIONS = 15000;
    /**
     * 发送post请求访问restful接口
     *
     * @param url
     * @param json
     * @return
     */
    public static String doRestfulPost(String url, String json) {
        String result = "";
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");

            httpConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            while ((output = responseBuffer.readLine()) != null) {
                result = output;
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 发送get请求访问restful接口
     *
     * @param url
     * @param json
     * @return
     */
    public static String doRestfulGet(String url, String json) {
        String result = "";
        try {
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));
            String output;
            System.out.println("Output from Server:  \n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的URL
     * @param rawParams 请求参数 ，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPostHost(String url, Map<String, String> rawParams) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            StringBuffer paramStr = new StringBuffer();
            // 如果传递参数个数比较多的话可以对传递的参数进行封装
            for (Map.Entry<String, String> entry : rawParams.entrySet()) {
                // 封装请求参数
                paramStr.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            String param = "";
            if (paramStr.length() > 0) {
                param = paramStr.deleteCharAt(paramStr.length() - 1).toString();
            }
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 使用finally块来关闭输出流、输入流
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 发送post请求访问restful接口
     *
     * @param url
     * @param json
     * @return
     */
    public static String doRestfulPost(String url,Map<String, String> headParams, String json) {
        String result = "";
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            //添加头部参数
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                httpConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            while ((output = responseBuffer.readLine()) != null) {
                result = output;
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        // 读取响应输入流
        BufferedReader in = null;
        // 存储参数
        StringBuffer sb = new StringBuffer();
        // 编码之后的参数
        String params = "";
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            System.out.println(full_url);
            // 创建URL对象
            URL connURL = new URL(full_url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
