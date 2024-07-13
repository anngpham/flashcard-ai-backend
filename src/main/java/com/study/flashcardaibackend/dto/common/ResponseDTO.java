package com.study.flashcardaibackend.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private boolean isSuccess = true;
    private T data;

    public ResponseDTO() {
    }

    public ResponseDTO(T data) {
        this.data = data;
    }

    public ResponseDTO(T data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
    }
}
