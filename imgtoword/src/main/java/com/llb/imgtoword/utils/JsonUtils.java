package com.llb.imgtoword.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * json格式转换
 * @Author llb
 * Date on 2019/12/6
 */
public class JsonUtils {

    /**
     * 将百度接口返回的JSONObject转换为Alibaba的fastJson
     * @param jsonObject
     * @return
     */
    public static JSONObject orgJsonToAlibaba(org.json.JSONObject jsonObject) {

        return JSONObject.parseObject(jsonObject.toString());
    }
}
