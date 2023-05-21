package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EpRecruitMvcConfig implements WebMvcConfigurer {
    @Value("${getEpRecruitPath}")
    String getEpRecruitPath;
    @Value("${getEpRecruitThumbnailPath}")
    String getEpRecruitThumbnailPath;
    @Value("${getEpRecruitMainThumbnailPath}")
    String getEpRecruitMainThumbnailPath;
    @Value("${uploadEpRecruit}")
    String uploadEpRecruit;
    @Value("${getEpRecruitContentPath}")
    String getEpRecruitContentPath;
    @Value("${uploadEpRecruitContent}")
    String uploadEpRecruitContent;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadEpRecruit).addResourceLocations(getEpRecruitMainThumbnailPath);
        registry.addResourceHandler(uploadEpRecruitContent).addResourceLocations(getEpRecruitContentPath);
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
//        return commonsMultipartResolver;
//    }
}



