package com.kim.dibt.security.handlers;


import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.ErrorResult;
import com.kim.dibt.exceptions.CustomRuntimeException;
import com.kim.dibt.security.repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            invalidTokenResponse(response);
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        } else {
            invalidTokenResponse(response);
        }
    }

    private void invalidTokenResponse(HttpServletResponse response) {
        ErrorResult result = ErrorResult.of(CoreConstants.INVALID_TOKEN);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getWriter().write(result.toString());
            throw new CustomRuntimeException(CoreConstants.INVALID_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
