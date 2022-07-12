package com.materiais.materiais.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    //Erro produto não encontrado
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handlerException(Exception e) {
        log.info("Material inexistente!");
        return new ResponseEntity("Material inexistente", HttpStatus.NOT_FOUND);
    }

    //Erro para valores nulos
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handlerException2(Exception e) {
        log.info("Erro, valor nulo!");
        return new ResponseEntity("Erro, valor nulo!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerException3(Exception e) {
        log.info("O corpo está vazio!");
        return new ResponseEntity("Erro, corpo da requisição está vazio!", HttpStatus.BAD_REQUEST);
    }



}
