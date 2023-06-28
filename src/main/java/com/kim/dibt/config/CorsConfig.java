package com.kim.dibt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // This annotation is needed to enable CORS
public class CorsConfig {
    @Value("${allowed.origin}")
    private String allowedOrigin;

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigin, "http://localhost:4200","https://test-up-dibt.vercel.app")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                ;
            }
        };
    }
}


//package com.kim.dibt.config;
//
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.lang.NonNull;
//        import org.springframework.web.servlet.config.annotation.CorsRegistry;
//        import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//        import java.util.List;
//
//@Configuration
//public class CorsOriginConfig {
//
//    @Value("${cors.allowed-origins}")
//    String[] origins;
//    @Value("${cors.allowed-methods}")
//    String[] methods;
//    @Value("${cors.allowed-headers}")
//    String[] headers;
//    @Value("${cors.allow-credentials}")
//    boolean credentials;
//    @Value("${cors.exposed-headers}")
//    String[] exposedHeaders;
//    @Value("${cors.max-age}")
//    long maxAge;
//    @Bean
//    public WebMvcConfigurer getCorsConfiguration() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(@NonNull CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(origins)
//                        .allowedMethods(methods)
//                        .allowedHeaders(headers)
//                        .allowCredentials(credentials)
//                        .exposedHeaders(exposedHeaders)
//                        .maxAge(maxAge);
//            }
//        };
//    }
//
//}



