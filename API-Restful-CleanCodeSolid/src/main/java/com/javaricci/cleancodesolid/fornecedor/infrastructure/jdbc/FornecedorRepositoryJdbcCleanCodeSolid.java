package com.javaricci.cleancodesolid.fornecedor.infrastructure.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.port.FornecedorRepositoryPortCleanCodeSolid;

@Repository
public class FornecedorRepositoryJdbcCleanCodeSolid
        implements FornecedorRepositoryPortCleanCodeSolid {

    private final JdbcTemplate jdbcTemplate;
    private final FornecedorRowMapperCleanCodeSolid rowMapper;

    public FornecedorRepositoryJdbcCleanCodeSolid(
            JdbcTemplate jdbcTemplate,
            FornecedorRowMapperCleanCodeSolid rowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<FornecedorCleanCodeSolid> buscarTodos() {

        String sql =
                "SELECT * FROM FORNECEDORES";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<FornecedorCleanCodeSolid> buscarPorId(Long id) {

        String sql =
                "SELECT * FROM FORNECEDORES WHERE ID = ?";

        List<FornecedorCleanCodeSolid> lista =
                jdbcTemplate.query(
                        sql,
                        rowMapper,
                        id);

        return lista.stream().findFirst();
    }

    @Override
    public FornecedorCleanCodeSolid salvar(
            FornecedorCleanCodeSolid fornecedor) {

        if (fornecedor.isNovoCadastro()) {
            return inserir(fornecedor);
        }

        atualizar(fornecedor);

        return fornecedor;
    }

    private FornecedorCleanCodeSolid inserir(
            FornecedorCleanCodeSolid fornecedor) {

        String sql =
                "INSERT INTO FORNECEDORES "
                + "(RAZAOSOCIALFORNECEDOR,"
                + "CNPJFORNECEDOR,"
                + "ENDERECO,"
                + "BAIRRO,"
                + "MUNICIPIO,"
                + "CEP)"
                + " VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS);

            ps.setString(
                    1,
                    fornecedor.getRazaoSocial());

            ps.setString(
                    2,
                    fornecedor.getCnpj());

            ps.setString(
                    3,
                    fornecedor.getEndereco());

            ps.setString(
                    4,
                    fornecedor.getBairro());

            ps.setString(
                    5,
                    fornecedor.getMunicipio());

            ps.setString(
                    6,
                    fornecedor.getCep());

            return ps;

        }, keyHolder);

        fornecedor.setId(
                keyHolder.getKey().longValue());

        return fornecedor;
    }

    private void atualizar(
            FornecedorCleanCodeSolid fornecedor) {

        String sql =
                "UPDATE FORNECEDORES SET "
                + "RAZAOSOCIALFORNECEDOR=?,"
                + "CNPJFORNECEDOR=?,"
                + "ENDERECO=?,"
                + "BAIRRO=?,"
                + "MUNICIPIO=?,"
                + "CEP=? "
                + "WHERE ID=?";

        jdbcTemplate.update(
                sql,
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getEndereco(),
                fornecedor.getBairro(),
                fornecedor.getMunicipio(),
                fornecedor.getCep(),
                fornecedor.getId());
    }

    @Override
    public void excluir(Long id) {

        jdbcTemplate.update(
                "DELETE FROM FORNECEDORES WHERE ID=?",
                id);
    }
}