package com.kim.dibt.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsOriginConfig {

    @Value("${cors.allowed-origins}")
    List<String> origins;
    @Value("${cors.allowed-methods}")
    List<String> methods;
    @Value("${cors.allowed-headers}")
    List<String> headers;
    @Value("${cors.allow-credentials}")
    boolean credentials;
    @Value("${cors.exposed-headers}")
    List<String> exposedHeaders;
    @Value("${cors.max-age}")
    long maxAge;

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(String.valueOf(origins))
                        .allowedMethods(String.valueOf(methods))
                        .allowedHeaders(String.valueOf(headers))
                        .exposedHeaders(String.valueOf(exposedHeaders))
                        .maxAge(maxAge)
                        .allowCredentials(credentials);
            }
        };
    }

}
