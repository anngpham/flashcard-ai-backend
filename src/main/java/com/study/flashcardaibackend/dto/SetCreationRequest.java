package com.study.flashcardaibackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCreationRequest {

    @NotBlank(message = "title not blank")
    private String title;

    private String description;
}
