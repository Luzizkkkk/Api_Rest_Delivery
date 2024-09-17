package br.edu.ifpr.api_delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.api_delivery.model.Categoria;
import br.edu.ifpr.api_delivery.repositorio.CategoriaRepositorio;

@RestController
@RequestMapping("/categorias")
public class CategoriaControlador {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaRepositorio.save(categoria);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }
}
