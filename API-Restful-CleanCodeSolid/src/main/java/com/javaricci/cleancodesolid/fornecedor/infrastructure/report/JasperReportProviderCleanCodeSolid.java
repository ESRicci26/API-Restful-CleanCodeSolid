package com.javaricci.cleancodesolid.fornecedor.infrastructure.report;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

@Component
public class JasperReportProviderCleanCodeSolid {

    public JasperReport obterRelatorioCompilado()
            throws Exception {

        InputStream inputStream =
                getClass().getResourceAsStream(
                        "/reports/FornecedoresCleanCodeSolid.jrxml");

        if (inputStream == null) {

            throw new RuntimeException(
                    "Arquivo JRXML não encontrado.");
        }

        return JasperCompileManager.compileReport(
                inputStream);
    }
}