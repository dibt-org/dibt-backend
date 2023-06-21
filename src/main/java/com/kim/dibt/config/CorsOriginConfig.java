package com.kim.dibt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsOriginConfig {

    @Value("${cors.allowed-origins}")
    String[] origins;
    @Value("${cors.allowed-methods}")
    String[] methods;
    @Value("${cors.allowed-headers}")
    String[] headers;
    @Value("${cors.allow-credentials}")
    boolean credentials;
    @Value("${cors.exposed-headers}")
    String[] exposedHeaders;
    @Value("${cors.max-age}")
    long maxAge;
    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(origins)
                        .allowedMethods(methods)
                        .allowedHeaders(headers)
                        .allowCredentials(credentials)
                        .exposedHeaders(exposedHeaders)
                        .maxAge(maxAge);
            }
        };
    }

}

