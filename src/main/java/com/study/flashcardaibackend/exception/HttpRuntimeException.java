package com.study.flashcardaibackend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;

    public HttpRuntimeException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
