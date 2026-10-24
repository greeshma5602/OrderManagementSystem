package com.example.orderService.DTO;



import com.example.orderService.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

        private Long orderId;
        private String orderNumber;
        private double totalAmount;
        private String status;
        private LocalDateTime orderDate;
        private List<OrderItemResponse> items;
        private PaymentStatus paymentStatus;


    }

