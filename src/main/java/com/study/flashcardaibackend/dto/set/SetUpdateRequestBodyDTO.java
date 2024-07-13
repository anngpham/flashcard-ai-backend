package com.study.flashcardaibackend.dto.set;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUpdateRequestBodyDTO {

    @Nullable
    @Size(max = 80)
    private String title;

    @Nullable
    @Size(max = 100)
    private String description;
}
