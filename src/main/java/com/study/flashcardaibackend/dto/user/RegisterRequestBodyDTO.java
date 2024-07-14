package com.study.flashcardaibackend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestBodyDTO {
    @NotBlank(message = "email not empty")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password not empty")
    @Size(min = 2, max = 20, message = "password length must be between 2 and 20")
    private String password;
}
