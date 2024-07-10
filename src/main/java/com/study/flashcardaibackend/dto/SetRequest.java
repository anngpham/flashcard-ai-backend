package com.study.flashcardaibackend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetRequest {

    @NotBlank(message = "title not blank")
    private String title;

    @Nullable
    @Size(max = 100)
    private String description;
}
