package com.javaricci.cleancodesolid.fornecedor.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.port.FornecedorRepositoryPortCleanCodeSolid;

@Service
public class BuscarFornecedorUseCaseCleanCodeSolid {

    private final FornecedorRepositoryPortCleanCodeSolid repository;

    public BuscarFornecedorUseCaseCleanCodeSolid(
            FornecedorRepositoryPortCleanCodeSolid repository) {

        this.repository = repository;
    }

    public Optional<FornecedorCleanCodeSolid>
            executar(Long id) {

        return repository.buscarPorId(id);
    }
}