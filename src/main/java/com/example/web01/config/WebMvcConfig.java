package com.example.web01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    // application.properties에 설정한 "uploadPath" 프로퍼티 값을 읽어와서 설정
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //WebMvcConfigurer.super.addResourceHandlers(registry);

        // url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
