package com.kim.dibt.security.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.ErrorResult;
import com.kim.dibt.exceptions.CustomIOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper jsonMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResult result;
        if (authException instanceof BadCredentialsException || authException instanceof UsernameNotFoundException) {
            result = ErrorResult.of(CoreConstants.INVALID_CREDENTIALS);
            try {
                response.getWriter().write(jsonMapper.writeValueAsString(result));
            } catch (Exception e) {
                throw new CustomIOException(e.getMessage(), e);
            }
        } else {
            result = ErrorResult.of(CoreConstants.AUTHENTICATION_FAILED);
            try {
                response.getWriter().write(jsonMapper.writeValueAsString(result));
            } catch (Exception e) {
                throw new CustomIOException(e.getMessage(), e);
            }
        }
    }
}

