package com.javaricci.cleancodesolid.fornecedor.application.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.infrastructure.report.JasperReportProviderCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.ListarFornecedorUseCaseCleanCodeSolid;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class GerarRelatorioFornecedorServiceCleanCodeSolid {

    private final JasperReportProviderCleanCodeSolid reportProvider;

    private final ListarFornecedorUseCaseCleanCodeSolid listarUseCase;

    public GerarRelatorioFornecedorServiceCleanCodeSolid(
            JasperReportProviderCleanCodeSolid reportProvider,
            ListarFornecedorUseCaseCleanCodeSolid listarUseCase) {

        this.reportProvider = reportProvider;
        this.listarUseCase = listarUseCase;
    }

    public byte[] gerarRelatorioPdf()
            throws Exception {

        JasperReport report =
                reportProvider.obterRelatorioCompilado();

        List<FornecedorCleanCodeSolid> fornecedores =
                listarUseCase.executar();

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(
                        fornecedores);

        Map<String, Object> parametros =
                new HashMap<>();

        parametros.put(
                "TITULO_RELATORIO",
                "Relatório de Fornecedores");

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        report,
                        parametros,
                        dataSource);

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        JasperExportManager.exportReportToPdfStream(
                jasperPrint,
                output);

        return output.toByteArray();
    }
}