package com.study.flashcardaibackend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBodyDTO {
    @NotBlank(message = "email not empty")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password not empty")
    private String password;
}
