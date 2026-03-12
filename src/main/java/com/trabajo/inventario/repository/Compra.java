package com.trabajo.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trabajo.inventario.entity.Compra;

public interface RepositorioCompra extends JpaRepository<Compra, Integer> {

}