package br.com.laboratory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleSingleLoadRequest(IllegalArgumentException ex) {
        log.warn("Argumento informado não é válido msg={} ex={}",ex.getMessage(),ex);
        return   ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleInformationNotFound(EmptyResultDataAccessException ex) {
        log.warn("Não existe na base de dados msg={} ex={}",ex.getMessage(),ex);
        return   ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<Object> handleBindException(BindException ex) {
        log.info("Parametros invalidos  no request msg={} ex={}", ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.warn("Erro no servidor msg={} ex={}",ex.getMessage(),ex);
        return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }



}
