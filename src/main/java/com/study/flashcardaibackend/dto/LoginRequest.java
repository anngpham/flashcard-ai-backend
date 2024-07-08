package com.study.flashcardaibackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "email not empty")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password not empty")
    private String password;
}
