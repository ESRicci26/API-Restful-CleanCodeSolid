package com.javaricci.cleancodesolid.fornecedor.presentation.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javaricci.cleancodesolid.fornecedor.application.usecase.BuscarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.ExcluirFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.ListarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.SalvarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.service.GerarRelatorioFornecedorServiceCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;

@RestController
@RequestMapping("/api/fornecedores")
public class ApiFornecedorControllerCleanCodeSolid {

    private final ListarFornecedorUseCaseCleanCodeSolid listarUseCase;
    private final BuscarFornecedorUseCaseCleanCodeSolid buscarUseCase;
    private final SalvarFornecedorUseCaseCleanCodeSolid salvarUseCase;
    private final ExcluirFornecedorUseCaseCleanCodeSolid excluirUseCase;
    private final GerarRelatorioFornecedorServiceCleanCodeSolid relatorioService;

    public ApiFornecedorControllerCleanCodeSolid(
            ListarFornecedorUseCaseCleanCodeSolid listarUseCase,
            BuscarFornecedorUseCaseCleanCodeSolid buscarUseCase,
            SalvarFornecedorUseCaseCleanCodeSolid salvarUseCase,
            ExcluirFornecedorUseCaseCleanCodeSolid excluirUseCase,
            GerarRelatorioFornecedorServiceCleanCodeSolid relatorioService) {

        this.listarUseCase = listarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.salvarUseCase = salvarUseCase;
        this.excluirUseCase = excluirUseCase;
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public List<FornecedorCleanCodeSolid> listarTodos() {
        return listarUseCase.executar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorCleanCodeSolid> buscarPorId(
            @PathVariable Long id) {

        Optional<FornecedorCleanCodeSolid> fornecedor =
                buscarUseCase.executar(id);

        return fornecedor
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FornecedorCleanCodeSolid> salvar(
            @RequestBody FornecedorCleanCodeSolid fornecedor) {

        return ResponseEntity.ok(
                salvarUseCase.executar(fornecedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorCleanCodeSolid> atualizar(
            @PathVariable Long id,
            @RequestBody FornecedorCleanCodeSolid fornecedor) {

        fornecedor.setId(id);

        return ResponseEntity.ok(
                salvarUseCase.executar(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        excluirUseCase.executar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio()
            throws Exception {

        byte[] pdf =
                relatorioService.gerarRelatorioPdf();

        return ResponseEntity.ok()
                .header(
                        "Content-Type",
                        "application/pdf")
                .header(
                        "Content-Disposition",
                        "inline; filename=fornecedores.pdf")
                .body(pdf);
    }
}