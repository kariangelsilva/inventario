package com.trabajo.inventario.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trabajo.inventario.dto.ProductsRequestDTO;
import com.trabajo.inventario.dto.ProductsResponseDTO;
import com.trabajo.inventario.entity.Products;
import com.trabajo.inventario.repository.ProductsRepository;



@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ProductsResponseDTO createProducts(ProductsRequestDTO dto) {
        Products product = new Products();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        Products saved = productsRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductsResponseDTO> getProducts() {
        List<Products> products = productsRepository.findAll();
        List<ProductsResponseDTO> list = new ArrayList<>();
        for (Products p : products) {
            list.add(toResponse(p));
        }
        return list;
    }

    public ProductsResponseDTO getProductById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return toResponse(product);
    }

    public ProductsResponseDTO updateProduct(Long id, ProductsRequestDTO dto) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        Products updated = productsRepository.save(product);
        return toResponse(updated);
    }

    public void deleteProduct(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productsRepository.deleteById(id);
    }

    private ProductsResponseDTO toResponse(Products product) {
        ProductsResponseDTO response = new ProductsResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }

        * @param id       ID del producto
     * @param cantidad Cantidad a sumar (debe ser > 0)
     */
    public ProductsResponseDTO increaseStock(Long id, Long cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        product.setStock(product.getStock() + cantidad);
        return toResponse(productsRepository.save(product));
    }

      @param id       
      @param cantidad
    public ProductsResponseDTO decreaseStock(Long id, Long cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        if (product.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + product.getStock());
        }
        product.setStock(product.getStock() - cantidad);
        return toResponse(productsRepository.save(product));
    }

    private ProductsResponseDTO toResponse(Products product) {
        ProductsResponseDTO response = new ProductsResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }
}
