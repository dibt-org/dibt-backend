package com.kim.dibt.cloudinaryApi;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kim.dibt.adapters.CloudinaryAdapterManager;
import com.kim.dibt.adapters.ICloudinaryAdapterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api-key}")
    String APIKey;

    @Value("${cloudinary.api-secret}")
    String APISecret;

    @Bean
    public Cloudinary cloudinaryUser() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dnz0k9kgp",
                "api_key", APIKey,
                "api_secret", APISecret));
    }


    @Bean
    public ICloudinaryAdapterService iCloudinaryAdapterService() {
        return new CloudinaryAdapterManager(cloudinaryUser());
    }
}
