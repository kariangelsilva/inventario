package com.trabajo.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabajo.inventario.dto.ProductsRequestDTO;
import com.trabajo.inventario.dto.ProductsResponseDTO;
import com.trabajo.inventario.services.ProductsService;




@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ProductsResponseDTO createProduct(@RequestBody ProductsRequestDTO request) {
        return productsService.createProducts(request);
    }

    @GetMapping
    public List<ProductsResponseDTO> getProducts() {
        return productsService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> updateProduct(@PathVariable Long id,@RequestBody ProductsRequestDTO request) {
        return ResponseEntity.ok(productsService.updateProduct(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

     @PatchMapping("/{id}/increase")
    public ResponseEntity<ProductsResponseDTO> increaseStock(@PathVariable Long id,
                                                             @RequestParam Long cantidad) {
        return ResponseEntity.ok(productsService.increaseStock(id, cantidad));
    }

    @PatchMapping("/{id}/decrease")
    public ResponseEntity<ProductsResponseDTO> decreaseStock(@PathVariable Long id,
                                                             @RequestParam Long cantidad) {
        return ResponseEntity.ok(productsService.decreaseStock(id, cantidad));
    }
}
