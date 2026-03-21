package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.jwt.AuthenticationService;
import com.example.jwtsecurity.request.AuthRequestDto;
import com.example.jwtsecurity.request.RegisterRequestDto;
import com.example.jwtsecurity.response.AuthResponseDto;
import com.example.jwtsecurity.response.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return authenticationService.register(registerRequestDto);
    }

    @PostMapping("/authenticate")
    public AuthResponseDto authenticate(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return authenticationService.authenticate(authRequestDto);
    }
}
