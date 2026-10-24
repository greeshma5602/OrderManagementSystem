package com.example.orderService.Controller;

import com.example.orderService.DTO.OrderResponse;
import com.example.orderService.entity.OrderStatus;
import com.example.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                orderService.placeOrder(username)
        );
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                orderService.cancelOrder(orderId, username)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                orderService.getOrders(username)
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status,
        @RequestHeader("X-User-Name") String username){

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status)
        );
    }
}