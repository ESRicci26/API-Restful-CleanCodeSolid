package com.javaricci.cleancodesolid.fornecedor.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaricci.cleancodesolid.fornecedor.application.usecase.BuscarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.ExcluirFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.ListarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.application.usecase.SalvarFornecedorUseCaseCleanCodeSolid;
import com.javaricci.cleancodesolid.fornecedor.domain.model.FornecedorCleanCodeSolid;

@Controller
@RequestMapping("/fornecedores")
public class WebFornecedorControllerCleanCodeSolid {

    private final ListarFornecedorUseCaseCleanCodeSolid listarUseCase;
    private final BuscarFornecedorUseCaseCleanCodeSolid buscarUseCase;
    private final SalvarFornecedorUseCaseCleanCodeSolid salvarUseCase;
    private final ExcluirFornecedorUseCaseCleanCodeSolid excluirUseCase;

    public WebFornecedorControllerCleanCodeSolid(
            ListarFornecedorUseCaseCleanCodeSolid listarUseCase,
            BuscarFornecedorUseCaseCleanCodeSolid buscarUseCase,
            SalvarFornecedorUseCaseCleanCodeSolid salvarUseCase,
            ExcluirFornecedorUseCaseCleanCodeSolid excluirUseCase) {

        this.listarUseCase = listarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.salvarUseCase = salvarUseCase;
        this.excluirUseCase = excluirUseCase;
    }

    @GetMapping
    public String listarTodos(Model model) {

        model.addAttribute(
                "fornecedores",
                listarUseCase.executar());

        return "fornecedores/listar";
    }

    @GetMapping("/novo")
    public String novoFornecedor(Model model) {

        model.addAttribute(
                "fornecedor",
                new FornecedorCleanCodeSolid());

        return "fornecedores/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute
            FornecedorCleanCodeSolid fornecedor) {

        salvarUseCase.executar(fornecedor);

        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "fornecedor",
                buscarUseCase.executar(id)
                        .orElse(
                                new FornecedorCleanCodeSolid()));

        return "fornecedores/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String excluir(
            @PathVariable Long id) {

        excluirUseCase.executar(id);

        return "redirect:/fornecedores";
    }
}