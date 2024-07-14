package com.study.flashcardaibackend.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDTO<T> {
    private boolean isSuccess = true;
    private T data;

    public SuccessResponseDTO() {
    }

    public SuccessResponseDTO(T data) {
        this.data = data;
    }
}
