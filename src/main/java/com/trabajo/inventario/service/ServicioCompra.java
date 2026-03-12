package com.trabajo.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trabajo.inventario.entity.Compra;
import com.trabajo.inventario.repository.RepositorioCompra;

@Service
public class ServicioCompra {

    private final RepositorioCompra repositorioCompra;

    public ServicioCompra(RepositorioCompra repositorioCompra) {
        this.repositorioCompra = repositorioCompra;
    }

    // Crear compra
    public Compra crearCompra(Compra compra) {
        compra.setEstado("PENDIENTE");
        return repositorioCompra.save(compra);
    }

    // Listar compras
    public List<Compra> listarCompras() {
        return repositorioCompra.findAll();
    }
}
