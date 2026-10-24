package com.example.orderService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String orderNumber;

        private double totalAmount;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        private LocalDateTime orderDate;

        private     Long userId;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        private List<OrderItem> items;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;



}




