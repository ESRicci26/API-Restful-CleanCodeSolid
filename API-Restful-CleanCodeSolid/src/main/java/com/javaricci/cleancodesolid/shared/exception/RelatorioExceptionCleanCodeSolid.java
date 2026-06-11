package com.javaricci.cleancodesolid.shared.exception;

public class RelatorioExceptionCleanCodeSolid
        extends RuntimeException {

    public RelatorioExceptionCleanCodeSolid(
            String mensagem,
            Throwable causa) {

        super(mensagem, causa);
    }
}