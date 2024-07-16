package com.study.flashcardaibackend.exception;

import com.study.flashcardaibackend.dto.common.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.flashcardaibackend.constant.ErrorConstants.API_NOT_FOUND;
import static com.study.flashcardaibackend.constant.ErrorConstants.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ValidationAdvice {
    Logger logger = LoggerFactory.getLogger(ValidationAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final List<String> errorList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        final ErrorResponseDTO validationErrorResponse = new ErrorResponseDTO(errorList);
        return ResponseEntity.status(exception.getStatusCode()).body(validationErrorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ErrorResponseDTO> handleRestException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(List.of(API_NOT_FOUND)));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleNoResourceException(NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(List.of(API_NOT_FOUND)));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        this.logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(List.of(API_NOT_FOUND)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations().stream()
                .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(errorMessages));
    }

    @ExceptionHandler(HttpRuntimeException.class)
    public final ResponseEntity<ErrorResponseDTO> handleHttpRuntimeException(HttpRuntimeException exception) {
        final ErrorResponseDTO httpErrorResponse = new ErrorResponseDTO(List.of(exception.getMessage()));
        return ResponseEntity.status(exception.getHttpStatus()).body(httpErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDTO> handleAllException(Exception exception) {
        this.logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(List.of(INTERNAL_SERVER_ERROR)));
    }

}
