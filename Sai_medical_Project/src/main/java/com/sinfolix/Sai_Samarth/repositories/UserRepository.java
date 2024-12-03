package com.sinfolix.Sai_Samarth.repositories;

import com.sinfolix.Sai_Samarth.entities.Order;
import com.sinfolix.Sai_Samarth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void deleteByUsername(String username);

    List<Order> findAllByUsername(String username);

}