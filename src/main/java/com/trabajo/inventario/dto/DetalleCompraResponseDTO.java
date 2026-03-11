package com.trabajo.inventario.dto;

import lombok.Data;

@Data
public class DetalleCompraResponseDTO {
    private Integer id;

    private Integer buys_id;

    private Integer products_id;

    private Integer amount;

    private Double unitPrice;

    private Double subtotal;
}
