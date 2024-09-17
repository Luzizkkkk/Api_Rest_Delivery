package br.edu.ifpr.api_delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.api_delivery.model.Pedido;
import br.edu.ifpr.api_delivery.repositorio.PedidoRepositorio;

@RestController
@RequestMapping("/pedidos")
public class PedidoControlador {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepositorio.findAll();
    }
}

