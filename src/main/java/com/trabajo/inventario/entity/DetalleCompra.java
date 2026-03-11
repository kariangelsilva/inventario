package com.trabajo.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 

@Table(name = "purchase_detail")          
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "buys_id")
    private Buys buys;
    
    @Column(name = "products_id")
    private Product products;

    @Column(name = "amount")
    private Integer amount;
    
    @Column(name = "unitPrice")
    private Double unitPrice;
    
    @Column(name = "subtotal")
    private Double subtotal;
    
    public void calcularSubtotal() {
        this.subtotal = this.amount * this.unitPrice;
    }
}
