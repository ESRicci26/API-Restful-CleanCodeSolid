package com.javaricci.cleancodesolid.fornecedor.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;

@Component
public class FornecedorRowMapperCleanCodeSolid
        implements RowMapper<FornecedorCleanCodeSolid> {

    @Override
    public FornecedorCleanCodeSolid mapRow(
            ResultSet rs,
            int rowNum)
            throws SQLException {

        FornecedorCleanCodeSolid fornecedor =
                new FornecedorCleanCodeSolid();

        fornecedor.setId(rs.getLong("id"));

        fornecedor.setRazaoSocial(
                rs.getString("RAZAOSOCIALFORNECEDOR"));

        fornecedor.setCnpj(
                rs.getString("CNPJFORNECEDOR"));

        fornecedor.setEndereco(
                rs.getString("ENDERECO"));

        fornecedor.setBairro(
                rs.getString("BAIRRO"));

        fornecedor.setMunicipio(
                rs.getString("MUNICIPIO"));

        fornecedor.setCep(
                rs.getString("CEP"));

        return fornecedor;
    }
}