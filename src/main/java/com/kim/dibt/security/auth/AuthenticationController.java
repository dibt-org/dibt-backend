package com.kim.dibt.security.auth;

import jakarta.validation.Valid;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.security.dto.LoginRequest;
import com.kim.dibt.security.dto.AuthenticationResponse;
import com.kim.dibt.security.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<DataResult<AuthenticationResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        AuthenticationResponse register = service.register(request);
        SuccessDataResult<AuthenticationResponse> result = new SuccessDataResult<>(register, CoreConstants.REGISTRATION_SUCCESS);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthenticationResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(new SuccessDataResult<>(service.login(request), CoreConstants.LOGIN_SUCCESS));
    }




}
