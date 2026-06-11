package com.javaricci.cleancodesolid.fornecedor.application.usecase;
import com.javaricci.cleancodesolid.fornecedor.domain.port.FornecedorRepositoryPortCleanCodeSolid;
import org.springframework.stereotype.Service;

@Service
public class ExcluirFornecedorUseCaseCleanCodeSolid {

    private final FornecedorRepositoryPortCleanCodeSolid repository;

    public ExcluirFornecedorUseCaseCleanCodeSolid(
            FornecedorRepositoryPortCleanCodeSolid repository) {

        this.repository = repository;
    }

    public void executar(Long id) {

        repository.excluir(id);
    }
}