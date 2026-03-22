package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.jwt.AuthenticationService;
import com.example.jwtsecurity.request.AuthRequestDto;
import com.example.jwtsecurity.request.RegisterRequestDto;
import com.example.jwtsecurity.request.ResendOTPRequestDto;
import com.example.jwtsecurity.request.VerifyOtpRequestDto;
import com.example.jwtsecurity.response.AuthResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

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
}
