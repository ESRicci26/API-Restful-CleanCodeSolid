package com.javaricci.cleancodesolid.shared.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandlerCleanCodeSolid {

    @ExceptionHandler(
            RelatorioExceptionCleanCodeSolid.class)
    public ResponseEntity<String> tratarErroRelatorio(
            RelatorioExceptionCleanCodeSolid ex) {

        return ResponseEntity.internalServerError()
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            IllegalArgumentException.class)
    public ResponseEntity<String> tratarValidacao(
            IllegalArgumentException ex) {

        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }
}