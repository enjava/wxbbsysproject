package com.en.test.utils;

/**
 * Created by en on 2016/8/12.
 */

import net.sf.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求校验工具类
 *
 * @author aguang
 * @Email zhaoguiguang@yeah.net
 *
 */
public class SignUtil {
   private static boolean first=false;
    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce) {
        String[] arr = new String[] { WeiXinContants.TOKEN, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信

        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    /**
     * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param createTime 消息创建时间
     * @return
     */
    public static String formatTime(String createTime) {
        // 将微信传入的CreateTime转换成long类型，再乘以1000
        long msgCreateTime = Long.parseLong(createTime) * 1000L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(msgCreateTime));
    }

    /**
     * 将date转换成 微信消息中的CreateTime时间戳
     *
     * @param createTime 消息创建时间
     * @return
     */
    public static String timeStamp(Date createTime) {
        long msgCreateTime =createTime.getTime();
        msgCreateTime=msgCreateTime/1000;
        return  msgCreateTime+"";
    }

    /**
     * 转换成时间戳
     * @return
     */
    public static String timeStamp()
    {
      return  timeStamp(new Date());

    }

    //获取access_token
    public static String getAccessToken() {
        String apiUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeiXinContants.APP_ID
                + "&secret=" + WeiXinContants.APP_SECRET;
        //

        JSONObject json = JSONObject.fromObject(HttpUtils.getInstance()
                .getJsonObjectByUrl(apiUrl));

        String access_token = json.get("access_token") + "";
        if (FileUtil.isEmpty(access_token)) {
            WeiXinContants.setAccessToken(access_token);
            FileUtil.log("access_token:->"+access_token);
        }
        return access_token;
    }


    public static String  getUserInfo(String openId) {
        if(first)
            getAccessToken();
        String apiUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + WeiXinContants.ACCESS_TOKEN+"&openid="
                + openId+"&lang=zh_CN";
        //  String apiUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=Zt6zazJLv45fRusiNYA7xcKGZHM4a7tjdFELeQXrNrp0ABOT2hlPnNukeD6D0iTPqEc1qomVgMQ1al5rP3zckekAbYPAaqjRvrjJJSwWNDlL28987bF7KprGA5ZNsCr-KDZcAGADWS&openid=o1YmajipuBCtAtwRCwUm0tcL1BNc"
       // + openId+"&lang=zh_CN";
       String userInfo= HttpUtils.getInstance().getJsonObjectByUrl(apiUrl);

        if (userInfo.indexOf("nickname")!=-1) {
            FileUtil.log("userInfo:->" + userInfo);
        }
        else
            getAccessToken();
        return userInfo;
    }
}