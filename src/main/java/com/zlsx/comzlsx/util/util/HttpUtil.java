package com.zlsx.comzlsx.util.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author houxm
 * @version 1.01 2018/4/16 11:55
 * @description HTTP请求工具类
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static <TResponse> TResponse httpGet(String url, String param, Map<String, String> requestProperty, Class<TResponse> tClass) throws Exception {
        String response = sendGet(url, param, requestProperty);
        return new ObjectMapper().readValue(response, tClass);
    }

    public static <TRequest, TResponse> TResponse httpPost(String url, TRequest request, Map<String, String> requestProperty, Class<TResponse> tClass) throws Exception {
        if (request == null) {
            throw new Exception("request 为null");
        }
        Map<String, Object> map = Reflect(request);
        String response = sendPost(url, map, requestProperty);
        return new ObjectMapper().readValue(response, tClass);
    }

    private static HashMap<String, Object> Reflect(Object obj) throws IllegalAccessException, IllegalArgumentException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            System.out.print(fields[j].getName() + ",");
            Object _Object = fields[j].get(obj);
            map.put(fields[j].getName(), _Object);
            System.out.println();
        }
        return map;
    }

    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */

    public static String sendGet(String url, String param, Map<String, String> requestProperty) {
        logger.info("get接口调用-请求url：" + url);
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url;
            if (!isEmpty(param)) {
                urlName = url + "?" + param;
            }
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            if (!CollectionUtils.isEmpty(requestProperty)) {
                setRequestProperty(conn, requestProperty);
            }
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                //result += "/n" + line;
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        logger.info("get接口调用-结果result：" + result);
        return result;
    }

    public static void setRequestProperty(URLConnection conn, Map<String, String> requestProperty) {
        if (CollectionUtils.isEmpty(requestProperty)) {
            return;
        }
        for (String key : requestProperty.keySet()) {
            conn.setRequestProperty(key, requestProperty.get(key));
        }
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url 发送请求的URL
     * @param map 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, Map<String, Object> map, Map<String, String> requestProperty) {
        logger.info("接口调用-url：" + url);
        String param = getUrlParamsByMap(map);
        logger.info("接口调用-param：" + param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //setRequestProperty(conn, requestProperty);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("接口调用-发送POST请求出现异常：" + e);
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
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
        logger.info("接口调用-结果result：" + result);
        return result;
    }

    public static String sendPost(String url, String param, Map<String, String> requestProperty) {
        logger.info("接口调用-url：" + url);
        logger.info("接口调用-param：" + param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            setRequestProperty(conn, requestProperty);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            OutputStream os = conn.getOutputStream();
            // 发送请求参数
            //out.print(param);

            os.write(param.getBytes("UTF-8"));
            // flush输出流的缓冲
            os.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("接口调用-发送POST请求出现异常：" + e);
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
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
        logger.info("接口调用-结果result：" + result);
        return result;
    }

    public static String sendPostForm(String url, Map<String, Object> map, Map<String, String> requestProperty) {
        String param = getUrlParamsByMap(map);
        return sendPostForm(url, param, requestProperty);
    }

    public static String sendPostForm(String url, String param, Map<String, String> requestProperty) {
        logger.info("接口调用-url：" + url);
        logger.info("接口调用-param：" + param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            setRequestProperty(conn, requestProperty);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("接口调用-发送POST请求出现异常：" + e);
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
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
        logger.info("接口调用-结果result：" + result);
        return result;
    }

    public static String getJsonData(JSONObject jsonParam, String urls) {
        logger.info("请求参数：" + JSON.toJSONString(jsonParam));
        StringBuffer sb = new StringBuffer();
        try {
            ;
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("Accept", "*/*");

            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json");


            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
//            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
//                System.out.println("连接成功");
            // 请求返回的数据
            InputStream in1 = conn.getInputStream();
            try {
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());

            } catch (Exception e1) {
                logger.info("请求错误1：" + JSON.toJSONString(e1));
                e1.printStackTrace();
            }
//            } else {
//                System.out.println("error++");
//            }

        } catch (Exception e) {
            logger.info("请求错误2：" + JSON.toJSONString(e));
            e.printStackTrace();
        }
        String responceStr = sb.toString();
        logger.info("请求结束,response：" + responceStr);
        return responceStr;
    }
}
