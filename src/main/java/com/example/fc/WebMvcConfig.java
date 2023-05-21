package com.example.fc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${noticeContentUploadPath}")
    String noticeContentUploadPath;
    @Value("${getNoticeContentPath}")
    String getNoticeContentPath;
    @Value("${uploadNoticeContent}")
    String uploadNoticeContent;
    @Value("${upload}")
    String upload;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadNoticeContent) // 웹 상에서 보여지는 맵핑경로.
                .addResourceLocations(getNoticeContentPath); // 실제로 주소(저장되는)하는 경로
    }
}
