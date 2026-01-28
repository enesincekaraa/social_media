package com.enesincekara.exception;

import lombok.Getter;

@Getter
public class BaseException   extends RuntimeException {
    private final ErrorType errorType;

    public BaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
