package com.javaricci.cleancodesolid.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfigCleanCodeSolid
        implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(
            CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}