package com.example.fc.memberJobHunting.memberJobHuntingConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MemberJobHuntingConfig implements WebMvcConfigurer {

    @Value("${getJobHuntingPath}")
    String getJobHuntingPath;
    @Value("${getJobHuntingThumbnailPath}")
    String getJobHuntingThumbnailPath;
    @Value("${getJobHuntingMainThumbnailPath}")
    String getJobHuntingMainThumbnailPath;
    @Value("${uploadJobHunting}")
    String uploadJobHunting;
    @Value("${getJobHuntingContentPath}")
    String getJobHuntingContentPath;
    @Value("${uploadJobHuntingContent}")
    String uploadJobHuntingContent;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadJobHunting).addResourceLocations(getJobHuntingMainThumbnailPath);
        registry.addResourceHandler(uploadJobHuntingContent).addResourceLocations(getJobHuntingContentPath);
    }
}
