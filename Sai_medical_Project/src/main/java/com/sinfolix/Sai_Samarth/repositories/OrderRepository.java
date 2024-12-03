package com.sinfolix.Sai_Samarth.repositories;

import com.sinfolix.Sai_Samarth.DTO.OrderDTO;
import com.sinfolix.Sai_Samarth.ENUM.StatusEnum;
import com.sinfolix.Sai_Samarth.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Integer> {
    List<Order> findByCustomerEmail(String customerEmail);

    @Query("SELECT o FROM Order o WHERE o.user.username = :username")
    List<Order> findOrdersByUsername(@Param("username") String username);

//    List<OrderDTO> findByOrderStatus();
//
//    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
//    List<Order> findByReviewed(boolean reviewed);
//    List<Order> findByClientName(String clientName);
}
