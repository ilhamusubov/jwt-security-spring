package com.example.jwtsecurity.exceptionHandler;

import com.example.jwtsecurity.enums.ErrorTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleAppException(CustomException ex) {

        HttpStatus status = mapStatus(ex.getErrorTypes());

        ErrorResponse response = new ErrorResponse(
                ex.getMessage()
        );
        return new ResponseEntity<>(response, status);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                ErrorTypes.SERVER_ERROR.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }



    // -------------------
    //  PRIVATE HELPER
    // -------------------
    private HttpStatus mapStatus(ErrorTypes errorTypes) {
        return switch (errorTypes) {
            case INVALID_INPUT -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BUSINESS_ERROR -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
