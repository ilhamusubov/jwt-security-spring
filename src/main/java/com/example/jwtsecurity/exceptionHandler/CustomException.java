package com.example.jwtsecurity.exceptionHandler;

import com.example.jwtsecurity.enums.ErrorTypes;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomException extends RuntimeException {

    private final ErrorTypes errorTypes;

    public CustomException(ErrorTypes errorTypes) {
        super(errorTypes.getMessage());
        this.errorTypes = errorTypes;
    }

    public CustomException(ErrorTypes errorTypes, String customMessage) {
        super(customMessage);
        this.errorTypes = errorTypes;
    }
}