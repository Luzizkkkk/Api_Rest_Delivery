package br.edu.ifpr.api_delivery.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpr.api_delivery.model.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
}
