package br.edu.ifpr.api_delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.api_delivery.model.Produto;
import br.edu.ifpr.api_delivery.repositorio.ProdutoRepositorio;

@RestController
@RequestMapping("/produtos")
public class ProdutoControlador {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepositorio.findAll();
    }
}

