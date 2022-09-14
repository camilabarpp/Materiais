package com.materiais.materiais.configuration.exception;

import com.materiais.materiais.configuration.errorresponse.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse resourceNotFoundException(IdNotFoundException e) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Dados não encontrados!")
                .field(NOT_FOUND.name())
                .parameter(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse nullPointerException(NullPointerException e) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Dados nulos ou incorretos!")
                .field(INTERNAL_SERVER_ERROR.name())
                .parameter(e.getClass().getSimpleName())
                .build();
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Método não suportado!")
                .field(METHOD_NOT_ALLOWED.name())
                .parameter(e.getClass().getSimpleName())
                .build();
    }
}
