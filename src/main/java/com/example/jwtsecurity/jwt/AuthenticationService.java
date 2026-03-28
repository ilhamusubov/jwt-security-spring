package com.example.jwtsecurity.jwt;

import com.example.jwtsecurity.entity.RefreshTokenEntity;
import com.example.jwtsecurity.enums.ErrorTypes;
import com.example.jwtsecurity.enums.Role;
import com.example.jwtsecurity.exceptionHandler.CustomException;
import com.example.jwtsecurity.mapper.UserMapper;
import com.example.jwtsecurity.repository.RefreshTokenRepository;
import com.example.jwtsecurity.repository.UserRepository;
import com.example.jwtsecurity.request.AuthRequestDto;
import com.example.jwtsecurity.request.RegisterRequestDto;
import com.example.jwtsecurity.request.ResendOTPRequestDto;
import com.example.jwtsecurity.request.VerifyOtpRequestDto;
import com.example.jwtsecurity.response.AuthResponseDto;
import com.example.jwtsecurity.service.EmailService;
import com.example.jwtsecurity.service.OTPService;
import com.example.jwtsecurity.user.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final OTPService otpService;

    private final EmailService emailService;

    private final RefreshTokenRepository refreshTokenRepository;


    public String register(RegisterRequestDto request){
        log.info("ActionLog.register.start");
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorTypes.USER_ALREADY_EXIST);
        }
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(userEntity);

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpService.saveOTP(request.getEmail(), otp);

        emailService.sendOtpEmail(request.getEmail(), otp);
        log.info("ActionLog.register.end");
        return "OTP sent to email";
    }


    public AuthResponseDto logIn(AuthRequestDto request){
            log.info("ActionLog.login.start");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserEntity user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.isVerified()) {
            throw new RuntimeException("User not verified. Please verify OTP first.");
        }
            String accessToken = jwtService.generateToken(user);
            RefreshTokenEntity refreshToken = createRefreshToken(user);
            log.info("ActionLog.login.end");
        return new AuthResponseDto(accessToken, refreshToken.getToken());

    }


    @Transactional
    public AuthResponseDto verifyOtp(VerifyOtpRequestDto request) {
        log.info("ActionLog.verifyOtp.start");
        String savedOtp = otpService.getOTP(request.getEmail());

        if (savedOtp == null || savedOtp.isEmpty()) {
            throw new RuntimeException("OTP expired or not found");
        }

        if (!savedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpService.deleteOTP(request.getEmail());

        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userEntity.setVerified(true);

        String accessToken = jwtService.generateToken(userEntity);
        RefreshTokenEntity refreshToken = createRefreshToken(userEntity);
        log.info("ActionLog.verifyOtp.end");
        return new AuthResponseDto(accessToken, refreshToken.getToken());
    }


    public String resendOtp(ResendOTPRequestDto request) {
        log.info("ActionLog.resendOtp.start");
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        String otp = String.format("%06d", new SecureRandom().nextInt(1000000));

        otpService.saveOTP(request.getEmail(), otp);
        emailService.sendOtpEmail(request.getEmail(), otp);
        log.info("ActionLog.resendOtp.end");
        return "OTP resent successfully";
    }


    public RefreshTokenEntity createRefreshToken(UserEntity userEntity) {
        log.info("ActionLog.createRefreshToken.start");
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(userEntity);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenEntity verifyToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
