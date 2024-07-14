package com.study.flashcardaibackend.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ErrorResponseDTO implements Serializable {
    private boolean isSuccess = false;
    private List<String> messages;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(List<String> messages) {
        this.messages = messages;
    }
}
