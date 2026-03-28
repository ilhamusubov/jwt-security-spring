package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.entity.RefreshTokenEntity;
import com.example.jwtsecurity.jwt.AuthenticationService;
import com.example.jwtsecurity.jwt.JwtService;
import com.example.jwtsecurity.request.*;
import com.example.jwtsecurity.response.AuthResponseDto;
import com.example.jwtsecurity.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return authenticationService.register(registerRequestDto);
    }

    @PostMapping("/authenticate")
    public AuthResponseDto logIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return authenticationService.logIn(authRequestDto);
    }

    @PostMapping("/verify-otp")
    public AuthResponseDto verifyOtp(@RequestBody VerifyOtpRequestDto request) {
        return authenticationService.verifyOtp(request);
    }

    @PostMapping("/resend-otp")
    public String resendOtp(@RequestBody @Valid ResendOTPRequestDto resendOTPRequestDto) {
        return authenticationService.resendOtp(resendOTPRequestDto);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@RequestBody RefreshTokenRequest request) {

        RefreshTokenEntity refreshToken = authenticationService.verifyToken(request.getRefreshToken());

        UserEntity user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(user);

        return new AuthResponseDto(newAccessToken, request.getRefreshToken());
    }
}
