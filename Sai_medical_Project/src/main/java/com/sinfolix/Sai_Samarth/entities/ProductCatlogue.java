package com.sinfolix.Sai_Samarth.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "product_catlogue")
@Data
public class ProductCatlogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(name = "discount",nullable = false)
    private int discount;

    @Column(name = "company_name",nullable = false)
    private String companyName;

    @Column(name = "medicine_name",nullable = false)
    private String medicineName;


    @Column(name = "minAge",nullable = false)
    private int minAge;

    @Column(name = "maxAge",nullable = false)
    private int maxAge;

    @Column(name = "real_mrp",nullable = false)
    private int realMrp;

    @Column(name = "discount_mrp",nullable = false)
    private int discountMpr;

    @Column(name = "prod_description", nullable = false)
    private String prodDescription;

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "comments" )
    private String comments;

    @Column(name = "product_image", nullable = false)
    private String image;

    private List<String> categories = new ArrayList<>();
}
