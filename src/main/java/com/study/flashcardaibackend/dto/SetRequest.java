package com.study.flashcardaibackend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetRequest {

    @NotBlank(message = "title not blank")
    private String title;

    @Nullable
    @Max(value = 100, message = "description max 100 characters")
    private String description;
}
