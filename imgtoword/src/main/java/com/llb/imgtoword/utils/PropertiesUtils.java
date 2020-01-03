package com.llb.imgtoword.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author llb
 * Date on 2019/12/6
 */
public class PropertiesUtils {

    private static Properties props;

    static {
        if(props == null) {
            try {
                Resource resource = new ClassPathResource("/application.properties");//
                props = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取属性
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return props == null ? null :  props.getProperty(key);

    }
    /**
     * 获取properyies属性
     * @return
     */
    public static Properties getProperties(){
        return props;
    }
}
