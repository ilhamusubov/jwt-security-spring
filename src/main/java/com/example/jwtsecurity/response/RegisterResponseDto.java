package com.example.jwtsecurity.response;


import com.example.jwtsecurity.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
