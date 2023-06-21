package com.kim.dibt.security.auth;

import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.security.dto.AuthenticationResponse;
import com.kim.dibt.security.dto.LoginRequest;
import com.kim.dibt.security.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;
    @Value("${cookie.access-token.max-age}")
    private int accessTokenExpiration;
    @Value("${cookie.refresh-token.max-age}")
    private int refreshTokenExpiration;

    @SecurityRequirement(name = "none")
    @PostMapping("/register")
    public ResponseEntity<Result> register(
            @RequestBody @Valid RegisterRequest request, HttpServletResponse response
    ) {
        var result = service.register(request);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        addCookie(response, CoreConstants.ACCESS_TOKEN, result.getData().getAccessToken(), accessTokenExpiration);
        addCookie(response, CoreConstants.REFRESH_TOKEN, result.getData().getRefreshToken(), refreshTokenExpiration);
        return ResponseEntity.ok(result);
    }

    @SecurityRequirement(name = "none")
    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthenticationResponse>> login(
            @RequestBody @Valid LoginRequest request, HttpServletResponse response
    ) {
        AuthenticationResponse login = service.login(request);
        addCookie(response, CoreConstants.ACCESS_TOKEN, login.getAccessToken(), accessTokenExpiration);
        addCookie(response, CoreConstants.REFRESH_TOKEN, login.getRefreshToken(), refreshTokenExpiration);
        return ResponseEntity.ok(new SuccessDataResult<>(login, CoreConstants.LOGIN_SUCCESS));
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // TODO document why this method is empty
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Result> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            HttpServletRequest request
    ) {
        var result = service.changePassword(oldPassword, newPassword, request);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
