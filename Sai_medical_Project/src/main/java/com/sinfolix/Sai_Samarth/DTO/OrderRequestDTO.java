package com.sinfolix.Sai_Samarth.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private List<OrderProductDTO> orderProductDTOList;
}
