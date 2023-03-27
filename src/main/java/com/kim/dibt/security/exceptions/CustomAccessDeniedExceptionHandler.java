package com.kim.dibt.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class CustomAccessDeniedExceptionHandler implements AccessDeniedHandler {
    private final ObjectMapper jsonMapper;

    private static final String TIMESTAMP = "timestamp";
    private static final String CODE = "code";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException
    ) throws RuntimeException {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        Map<String, Object> data = new HashMap<>();
        data.put(TIMESTAMP, new Date());
        data.put(CODE, httpStatus.value());
        data.put(STATUS, httpStatus.name());
        data.put(MESSAGE, accessDeniedException.getMessage());
        // send the response
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        try {
            response.getWriter().write(jsonMapper.writeValueAsString(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}


