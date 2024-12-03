package com.sinfolix.Sai_Samarth.service;

import com.sinfolix.Sai_Samarth.DTO.OrderDTO;
import com.sinfolix.Sai_Samarth.DTO.OrderProductDTO;

import java.util.List;

public interface OrderService {

    //    create a new order
    OrderDTO createOrder(OrderDTO orderDTO, List<OrderProductDTO> orderProductDTOList, String username);

    //    Get order List details by Customer email id
    List<OrderDTO> getOrderListByCustomerEmail(String customerEmail);

    //    Get Order Details by order ID
    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> findAllByUsername(String username);


//    List<OrderDTO> getAllPlacedOrders();
//


}
