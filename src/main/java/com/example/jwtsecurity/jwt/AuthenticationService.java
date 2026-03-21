package com.example.jwtsecurity.jwt;

import com.example.jwtsecurity.enums.Role;
import com.example.jwtsecurity.mapper.UserMapper;
import com.example.jwtsecurity.repository.UserRepository;
import com.example.jwtsecurity.request.AuthRequestDto;
import com.example.jwtsecurity.request.RegisterRequestDto;
import com.example.jwtsecurity.response.AuthResponseDto;
import com.example.jwtsecurity.response.RegisterResponseDto;
import com.example.jwtsecurity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    public RegisterResponseDto register(RegisterRequestDto request){

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(userEntity);

        return userMapper.userEntityToRegisterResponseDto(userEntity);
    }



    public AuthResponseDto authenticate(AuthRequestDto request){

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).
                    orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(userEntity);

            return new AuthResponseDto(token);

    }
}
