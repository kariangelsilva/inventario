package com.trabajo.inventario.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "purchase_detail")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buys_id")
    private Buys buys;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Products products;

    @Column(name = "amount")
    private Integer amount;
}
