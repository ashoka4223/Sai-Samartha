package com.sinfolix.Sai_Samarth.DTO;

import com.sinfolix.Sai_Samarth.entities.Order;
import com.sinfolix.Sai_Samarth.entities.ProductCatlogue;
import lombok.Data;

@Data
public class OrderProductDTO {


    private Order order;
    private ProductCatlogue productCatlogue;
    private int quantity;
}
