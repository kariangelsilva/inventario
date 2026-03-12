package com.trabajo.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.trabajo.inventario.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
}
