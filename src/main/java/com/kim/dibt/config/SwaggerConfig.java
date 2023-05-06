package com.kim.dibt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@OpenAPIDefinition(
        info = @Info(
                title = "DIBT API",
                version = "1.0",
                description = "DIBT API Documentation",
                contact = @Contact(
                        name = "KİM",
                        email = "info@kim.com",
                        url = "https://www.kim.com")
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth",
                        scopes = {"read", "write"}
                )
        }
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "JWT Token",
                        scheme = "bearer",
                        bearerFormat = "JWT",
                        type = SecuritySchemeType.HTTP,
                        in = SecuritySchemeIn.HEADER
                )
        }
)
public class SwaggerConfig {
}
