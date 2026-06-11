package com.javaricci.cleancodesolid.fornecedor.domain.port;

import java.util.List;
import java.util.Optional;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;

public interface FornecedorRepositoryPortCleanCodeSolid {

    List<FornecedorCleanCodeSolid> buscarTodos();

    Optional<FornecedorCleanCodeSolid> buscarPorId(Long id);

    FornecedorCleanCodeSolid salvar(
            FornecedorCleanCodeSolid fornecedor);

    void excluir(Long id);
}