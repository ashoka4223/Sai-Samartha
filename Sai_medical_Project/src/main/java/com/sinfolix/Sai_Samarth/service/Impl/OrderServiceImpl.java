package com.sinfolix.Sai_Samarth.service.Impl;

import com.sinfolix.Sai_Samarth.DTO.OrderDTO;
import com.sinfolix.Sai_Samarth.DTO.OrderProductDTO;
import com.sinfolix.Sai_Samarth.entities.Order;
import com.sinfolix.Sai_Samarth.entities.OrderProduct;
import com.sinfolix.Sai_Samarth.entities.User;
import com.sinfolix.Sai_Samarth.exceptions.ResourceNotFoundException;
import com.sinfolix.Sai_Samarth.repositories.OrderProductRepository;
import com.sinfolix.Sai_Samarth.repositories.OrderRepository;
import com.sinfolix.Sai_Samarth.repositories.ProductCatlogueRepo;
import com.sinfolix.Sai_Samarth.repositories.UserRepository;
import com.sinfolix.Sai_Samarth.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ProductCatlogueRepo productCatlogueRepo;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, List<OrderProductDTO> orderProductDTOList,
    String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Order order = new Order();
//        Set order Properties
        order.setId(orderDTO.getId());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setCustomerAddress(orderDTO.getCustomerAddress());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setModified_time(orderDTO.getModified_time());
        order.setStatus(orderDTO.getStatus());
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        for (OrderProductDTO orderProductDTO : orderProductDTOList) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(savedOrder);
            orderProduct.setProductCatlogue(productCatlogueRepo.findById(orderProductDTO.getProductCatlogue().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Catlogue", "ID ", orderProductDTO.getProductCatlogue().getId())));
            orderProduct.setQuantity(orderProductDTO.getQuantity());
            orderProductRepository.save(orderProduct);
        }
        return orderToOrderDTO(savedOrder);
    }


    // Return all the product ordered by specific customer
    @Override
    public List<OrderDTO> getOrderListByCustomerEmail(String customerEmail) {
        List<Order> orders  =  orderRepository.findByCustomerEmail(customerEmail);
        return orders.stream().map(e-> this.orderToOrderDTO(e)).collect(Collectors.toList());
    }

    // Return order by ID
    @Override
    public OrderDTO getOrderById(Long orderId) {
        return null;
    }

    //  Return all the placed orders
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(e-> this.orderToOrderDTO(e)).collect(Collectors.toList());
    }

    public List<OrderDTO> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Order> orders = orderRepository.findOrdersByUsername(username);
        return orders.stream().map(e-> this.orderToOrderDTO(e)).collect(Collectors.toList());
    }

    private OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName(order.getCustomerName());
        orderDTO.setCustomerEmail(order.getCustomerEmail());
        orderDTO.setCustomerAddress(order.getCustomerAddress());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setModified_time(order.getModified_time());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setId(order.getId());
        return orderDTO;

    }
}

