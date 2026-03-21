package com.example.jwtsecurity.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email(message = "Email düzgün formatda deyil")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[@#$%!]).{6,}$",
            message = "Şifrədə ən az 1 böyük hərf və 1 simvol olmalıdır"
    )
    @NotEmpty
    private String password;
}
