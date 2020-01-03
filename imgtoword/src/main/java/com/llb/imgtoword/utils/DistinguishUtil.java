package com.llb.imgtoword.utils;

import com.baidu.aip.ocr.AipOcr;

import java.util.HashMap;

/**
 * 识别图片文字
 * @Author llb
 * Date on 2019/12/6
 */
public class DistinguishUtil {

    //百度智能云创建应用获取信息
    public static String APP_ID = PropertiesUtils.getProperty("appid");
    public static String APP_KEY = PropertiesUtils.getProperty("appkey");
    public static String SECRET_KEY= PropertiesUtils.getProperty("secretkey");

    /**
     * 简单照片文字识别
     * @param client AipOcr
     * @param photoPath 图片路径
     * @return
     */
    public static com.alibaba.fastjson.JSONObject simpleDist(AipOcr client, String photoPath) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        org.json.JSONObject result = client.basicGeneral(photoPath, options);
        return JsonUtils.orgJsonToAlibaba(result);
    }

    /**
     * 图片文字识别高精度版
     * @param client AipOcr
     * @param photoPath 图片路径
     * @return
     */
    public static com.alibaba.fastjson.JSONObject highDist(AipOcr client, String photoPath) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");

        org.json.JSONObject result = client.basicAccurateGeneral(photoPath, options);
        return JsonUtils.orgJsonToAlibaba(result);
    }

    /**
     * 图片那文字识别，含生僻字
     * @param client
     * @param photoPath
     * @return
     */
    public static com.alibaba.fastjson.JSONObject rareDist(AipOcr client, String photoPath) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        org.json.JSONObject result = client.enhancedGeneral(photoPath, options);
        return JsonUtils.orgJsonToAlibaba(result);
    }

}
