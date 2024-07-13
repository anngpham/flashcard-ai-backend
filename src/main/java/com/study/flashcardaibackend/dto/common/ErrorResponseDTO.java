package com.study.flashcardaibackend.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponseDTO {
    private List<String> messages;
    private boolean isSuccess = false;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(List<String> messages) {
        this.messages = messages;
    }

    public ErrorResponseDTO(List<String> messages, boolean isSuccess) {
        this.messages = messages;
        this.isSuccess = isSuccess;
    }
}
