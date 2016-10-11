package com.en.test.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by en on 2016/8/12.
 */
public class FileUtil {

    /** 检查字符串是否为null或者空
     * @param str 需要检验的字符串
     * @return  如果为空或null返回false
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.trim().length() > 0)
            return true;
        return false;
    }

    /** 字符串转换成输入流
     * @param sInputString 输入的字符串
     * @return 转换为输入流
     */

    public static InputStream getStringToInputStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将一个输入流转化为字符串
     * @param tInputStream    输入流
     * @return  转换好的字符串
     */
    public static String getInputStreamToString(InputStream tInputStream) {
        if (tInputStream != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                StringBuffer tStringBuffer = new StringBuffer();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取的文本内容
     * @param fileName
     * @return 读取的文本内容
     */
    public static String readFile(String fileName) {
        String str = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String input;
            while ((input = br.readLine()) != null) {
                str += input + "\r\n";
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /** 文本内容写入文件 （从新写入非追加）
     * @param filePath 文件路径
     * @param content 要写入的文本
     */
    public static void writeFile(String filePath, String content) {
        BufferedWriter bufw = null;

        try {
            bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "GBK"));
            bufw.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufw != null) {
                    bufw.close();
                }
            } catch (IOException localIOException3) {
            }
        }

    }

    /**
     * @param fileName 文件名或者文件的路径
     * @param content
     */
    public static void writeAppend(String fileName, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, true);
            writer.write(content);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /** 记录日志
     * @param msg 文本内容
     */
    public static void log(String msg) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String writeMsg = df.format(new Date()) + ": " + msg;

        writeAppend("log.log", writeMsg + "\r\n");
    }
}
