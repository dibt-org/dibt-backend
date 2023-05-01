package com.kim.dibt.cloudinary_api;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api-key}")
    String apiKey;

    @Value("${cloudinary.api-secret}")
    String apiSecret;

    @Value("${cloudinary.cloud-name}")
    String cloudName;

    @Bean
    public Cloudinary cloudinaryUser() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                ));
    }
}
