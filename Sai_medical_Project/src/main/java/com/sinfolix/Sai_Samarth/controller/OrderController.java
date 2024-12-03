package com.sinfolix.Sai_Samarth.controller;

import com.sinfolix.Sai_Samarth.DTO.OrderDTO;
import com.sinfolix.Sai_Samarth.DTO.OrderProductDTO;
import com.sinfolix.Sai_Samarth.DTO.OrderRequestDTO;
import com.sinfolix.Sai_Samarth.DTO.UserDTO;
import com.sinfolix.Sai_Samarth.ENUM.StatusEnum;
import com.sinfolix.Sai_Samarth.entities.Order;
import com.sinfolix.Sai_Samarth.service.Impl.OrderServiceImpl;
import com.sinfolix.Sai_Samarth.service.Impl.UserService;
import com.sinfolix.Sai_Samarth.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "/**")
@Tag(name = "Order APIs")
// API Endpoint for Orders
public class OrderController {
    @Autowired
    OrderServiceImpl orderServiceImpl;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Product controller called "  + username);
        OrderDTO order = new OrderDTO();
        // map orderRequestDTO to Order
        order.setCustomerAddress(orderRequestDTO.getCustomerAddress());
        order.setCustomerEmail(orderRequestDTO.getCustomerEmail());
        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setOrderDate(LocalDate.now());
        order.setStatus(StatusEnum.ORDER_PLACED.getStatus());
        order.setModified_time(LocalDate.now());

        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        for (OrderProductDTO orderProductDTO : orderRequestDTO.getOrderProductDTOList()) {
            OrderProductDTO newOrderProductDTO = new OrderProductDTO();
            newOrderProductDTO.setProductCatlogue(orderProductDTO.getProductCatlogue());
            newOrderProductDTO.setQuantity(orderProductDTO.getQuantity());
            orderProductDTOList.add(newOrderProductDTO);
        }
        OrderDTO savedOrder = orderServiceImpl.createOrder(order, orderProductDTOList,username);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);


    }

    @GetMapping("/{email}")
    public List<OrderDTO> getOrderListByCustomerEmail(@PathVariable String email){
        return orderServiceImpl.getOrderListByCustomerEmail(email);
    }



    @GetMapping("/all")
    @Operation(summary = "Get all order placed by logged in user")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//        UserDTO user = userService.findByUsername(username);
        return new ResponseEntity<>( orderService.findAllByUsername(username),HttpStatus.OK);
    }

}

