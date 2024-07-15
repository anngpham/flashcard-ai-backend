package com.study.flashcardaibackend.dto.set;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCreationBodyDTO {
    @NotBlank(message = "title not blank")
    @Size(max = 80)
    private String title;

    @Nullable
    @Size(max = 100)
    private String description;
}
