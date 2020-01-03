package com.llb.imgtoword.utils;

import com.alibaba.fastjson.JSONPath;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * 百度识别文字工具类
 * @Author llb
 * Date on 2019/12/6
 */
public class BaiduOrc {

    //百度智能云创建应用获取信息
    public static String APP_ID = PropertiesUtils.getProperty("appid");
    public static String APP_KEY = PropertiesUtils.getProperty("appkey");
    public static String SECRET_KEY= PropertiesUtils.getProperty("secretkey");

    private static AipOcr aipOcr = new AipOcr(APP_ID, APP_KEY, SECRET_KEY);


    /**
     * 获取识别的文字，并且修改格式
     * @return
     */
    public static String getWords(String path) {
        //高精度文字识别
        com.alibaba.fastjson.JSONObject results = DistinguishUtil.highDist(aipOcr, path);
        String words = "";
        StringBuffer sb = new StringBuffer(words);
        //判断返回的条数
        Object size = JSONPath.eval(results, "$.words_result_num");
        for (int i = 0; i < (int)size; i++) {
            Object result = JSONPath.eval(results, "$.words_result[" + i + "].words");
            //这里我按照打字单要求，更换字符
            String str = result.toString().replaceAll("\\。", "。?");
            str = str.replaceAll("\\?", "\n");
            str = str.replaceAll("\\,", "，");
            str = str.replaceAll("\\!", "！\n");
            str = str.replaceAll("\\:", " ");
            str = str.replaceAll("\\“|\\”", " ");
            sb.append(str);
        }
        return sb.toString();
    }
    /**
     * 获取识别的文字，并且修改格式
     * @return
     */
    public static String getOrdinaryWords(String path) {
        //高精度文字识别
        com.alibaba.fastjson.JSONObject results = DistinguishUtil.highDist(aipOcr, path);
        String words = "";
        StringBuffer sb = new StringBuffer(words);
        //判断返回的条数
        Object size = JSONPath.eval(results, "$.words_result_num");
        for (int i = 0; i < (int)size; i++) {
            Object result = JSONPath.eval(results, "$.words_result[" + i + "].words");
            sb.append(result);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        AipOcr aipOcr = new AipOcr(APP_ID, APP_KEY, SECRET_KEY);
        String path = "C:\\Users\\ASUS\\Desktop\\E_9U$B~4$FDI_76PVJTHVAP.png";
        //简单文字识别
        //        com.alibaba.fastjson.JSONObject results = distinguishUtil.simpleDist(aipOcr, path);

        //高精度文字识别
        com.alibaba.fastjson.JSONObject results = DistinguishUtil.highDist(aipOcr, path);

        String words = "";
        StringBuffer sb = new StringBuffer(words);
        //判断返回的条数
        Object size = JSONPath.eval(results, "$.words_result_num");
        for (int i = 0; i < (int)size; i++) {
            Object result = JSONPath.eval(results, "$.words_result[" + i + "].words");
            //这里我按照打字单要求，更换字符
            String str = result.toString().replaceAll("\\?", " ");
            sb.append(str);
        }
        System.out.println(sb);
    }

}
