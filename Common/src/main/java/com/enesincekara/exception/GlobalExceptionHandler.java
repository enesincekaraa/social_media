package com.enesincekara.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorMessage> handleBaseException(BaseException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .code(errorType.getCode())
                        .message(errorType.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                        .build(),
                errorType.getHttpStatus()
        );
    }

    // Beklenmedik sistem hataları için (NullPointer, vb.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .code(ErrorType.INTERNAL_ERROR.getCode())
                        .message(ErrorType.INTERNAL_ERROR.getMessage() + ": " + ex.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                        .build(),
                ErrorType.INTERNAL_ERROR.getHttpStatus()
        );
    }
}