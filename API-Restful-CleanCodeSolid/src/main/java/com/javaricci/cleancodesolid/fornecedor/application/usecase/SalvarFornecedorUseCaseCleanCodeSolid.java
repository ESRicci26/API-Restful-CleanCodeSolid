package com.javaricci.cleancodesolid.fornecedor.application.usecase;
import org.springframework.stereotype.Service;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.port.FornecedorRepositoryPortCleanCodeSolid;

@Service
public class SalvarFornecedorUseCaseCleanCodeSolid {

    private final FornecedorRepositoryPortCleanCodeSolid repository;

    public SalvarFornecedorUseCaseCleanCodeSolid(
            FornecedorRepositoryPortCleanCodeSolid repository) {

        this.repository = repository;
    }

    public FornecedorCleanCodeSolid executar(
            FornecedorCleanCodeSolid fornecedor) {

        validar(fornecedor);

        return repository.salvar(fornecedor);
    }

    private void validar(
            FornecedorCleanCodeSolid fornecedor) {

        if (fornecedor.getRazaoSocial() == null
                || fornecedor.getRazaoSocial().isBlank()) {

            throw new IllegalArgumentException(
                    "Razão Social obrigatória");
        }
    }
}