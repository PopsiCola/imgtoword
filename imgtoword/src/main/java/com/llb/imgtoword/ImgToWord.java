package com.llb.imgtoword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author llb
 * Date on 2019/12/6
 */
@SpringBootApplication
public class ImgToWord extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(ImgToWord.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ImgToWord.class);
    }
}
