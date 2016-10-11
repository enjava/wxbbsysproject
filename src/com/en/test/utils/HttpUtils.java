package com.en.test.utils;

/**
 * Created by en on 2016/8/12.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * 饿汉试 单例 直接实例化一个，线程安全(以前一直用懒汉式，第一次调用实例化，但是有可能会出现多个实例，非常小的概率)
 *
 * @author aGuang
 *
 */
public class HttpUtils {

    private static HttpUtils instance = new HttpUtils();

    /**
     * 私有构造方法
     */
    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        return instance;
    }

    /**
     * 根据api访问地址获取地址返回的json数据
     *
     * @param apiUrl
     *
     * @return json
     */
    public String getJsonObjectByUrl(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String charSet = null;
            charSet = "utf-8";
            byte b[] = new byte[200];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlStream.read(b);
                if (numRead != -1) {
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            // System.out.println("content:" + content);
            JSONObject json = JSONObject.fromObject(new String(content
                    .getBytes(), "utf-8"));

            urlStream.close();
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据api访问地址获取地址返回的json数据
     *
     * @param apiUrl
     *
     * @return
     */
    public String getJsonArrayByUrl(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String charSet = null;
            charSet = "utf-8";
            byte b[] = new byte[200];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlStream.read(b);
                if (numRead != -1) {
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            // System.out.println("content:" + content);
            JSONArray array = JSONArray.fromObject(new String(content
                    .getBytes(), "utf-8"));

            urlStream.close();
            return array.toString();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据api访问地址获取地址返回的XML数据
     *
     * @param apiUrl
     *
     * @return
     */
    public String getXmlDataByUrl(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String charSet = null;
            charSet = "utf-8";
            byte b[] = new byte[6000];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlStream.read(b);
                if (numRead != -1) {
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            // System.out.println("content:" + content);
            String xmlString = new String(content.getBytes(), "utf-8");

            urlStream.close();
            return xmlString;
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * <b>用post请求访问一个http<b>
     *
     * @param apiUrl
     *            请求地址
     * @return
     */
    public String createPostHttpRequest(String apiUrl, String data)
            throws Exception {
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(apiUrl);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();
        // 25秒连接超时
        connection.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢,增大时间
        connection.setReadTimeout(25000);

        HttpURLConnection.setFollowRedirects(true);

        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // Set the post method. Default is GET
        connection.setRequestMethod("POST");
        // Post cannot use caches
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
        // connection.setFollowRedirects(true);

        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        connection.setInstanceFollowRedirects(true);
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        // connection.setRequestProperty("Content-Type",
        // "application/x-www-form-urlencoded");
        connection
                .setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
        connection.setRequestProperty("Referer", "https://api.weixin.qq.com/");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
        // String content = URLEncoder.encode("中国聚龙", "utf-8");
        String content = data;
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
        out.write(content);
        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
        String line = "";
        System.out.println("=============================");
        System.out.println("Contents of post request");
        System.out.println("=============================");
        while ((line = reader.readLine()) != null) {
            // line = new String(line.getBytes(), "utf-8");
            System.out.println("返回的信息:" + line);
        }
        System.out.println("=============================");
        System.out.println("Contents of post request ends");
        System.out.println("=============================");
        reader.close();
        connection.disconnect();
        return "";
    }
}
