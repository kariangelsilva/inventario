package com.trabajo.inventario.dto;



import lombok.Data;

@Data
public class ProductsResponseDTO {
    private Long id;

    private String name;

    private Double price;

    private Long  stock ;
    
}