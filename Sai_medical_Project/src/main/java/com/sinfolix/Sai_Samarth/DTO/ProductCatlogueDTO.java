package com.sinfolix.Sai_Samarth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCatlogueDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String image;
    private int quantity;
    private int price;
    private int discount;
    private String companyName;
    private String medicineName;
    private int minAge;
    private int maxAge;
    private int realMrp;
    private int discountMrp;
    private String prodDescription;
    private boolean disabled;
    private String comments;
    private List<String> categories = new ArrayList<>();

}
