package com.example.orderService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class OrderItemResponse {

        private Long productId;
        private int quantity;
        private double price;


    }
