package com.trabajo.inventario.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.trabajo.inventario.entity.Compra;
import com.trabajo.inventario.service.ServicioCompra;

@RestController
@RequestMapping("/compras")
public class ControladorCompra {

    private final ServicioCompra servicioCompra;

    public ControladorCompra(ServicioCompra servicioCompra) {
        this.servicioCompra = servicioCompra;
    }

    // Crear compra
    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return servicioCompra.crearCompra(compra);
    }

    // Listar compras
    @GetMapping
    public List<Compra> listarCompras() {
        return servicioCompra.listarCompras();
    }
}gi