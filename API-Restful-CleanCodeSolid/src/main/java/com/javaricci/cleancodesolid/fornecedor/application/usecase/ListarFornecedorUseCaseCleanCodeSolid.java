package com.javaricci.cleancodesolid.fornecedor.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.port.FornecedorRepositoryPortCleanCodeSolid;

@Service
public class ListarFornecedorUseCaseCleanCodeSolid {

    private final FornecedorRepositoryPortCleanCodeSolid repository;

    public ListarFornecedorUseCaseCleanCodeSolid(
            FornecedorRepositoryPortCleanCodeSolid repository) {

        this.repository = repository;
    }

    public List<FornecedorCleanCodeSolid> executar() {

        return repository.buscarTodos();
    }
}