package br.edu.ifpr.api_delivery.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpr.api_delivery.model.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaId(Long categoriaId);
}

