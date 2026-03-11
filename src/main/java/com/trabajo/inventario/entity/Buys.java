package com.trabajo.inventario.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "buys")
public class Buys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "state")
    private String state;
}
