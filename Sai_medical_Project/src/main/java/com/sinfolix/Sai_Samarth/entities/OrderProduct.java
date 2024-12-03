package com.sinfolix.Sai_Samarth.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_product")
@Data
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_catlogue_id", nullable = false)
    private ProductCatlogue productCatlogue;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
