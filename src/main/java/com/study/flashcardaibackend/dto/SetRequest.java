package com.study.flashcardaibackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetRequest {

    @NotBlank(message = "title not blank")
    private String title;

    private String description;
}
