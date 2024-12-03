package com.sinfolix.Sai_Samarth.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private LocalDate orderDate;
    private LocalDate modified_time;
    private int status;
}
