package com.kim.dibt.security.auth;

import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.security.dto.AuthenticationResponse;
import com.kim.dibt.security.dto.LoginRequest;
import com.kim.dibt.security.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @SecurityRequirement(name = "none")
    @PostMapping("/register")
    public ResponseEntity<Result> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        var result = service.register(request);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @SecurityRequirement(name = "none")
    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthenticationResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(new SuccessDataResult<>(service.login(request), CoreConstants.LOGIN_SUCCESS));
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

}
