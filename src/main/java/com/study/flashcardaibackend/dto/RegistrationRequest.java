package com.study.flashcardaibackend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank(message = "email not empty")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password not empty")
    private String password;
}
