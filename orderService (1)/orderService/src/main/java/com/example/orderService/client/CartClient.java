package com.example.orderService.client;

import com.example.orderService.DTO.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

    @GetMapping("/cart")
    CartResponse getCart(
            @RequestHeader("X-User-Name") String username
    );

    @DeleteMapping("/cart/{productId}")
    CartResponse removeFromCart(
            @PathVariable("productId") Long productId,
            @RequestHeader("X-User-Name") String username
    );
    @DeleteMapping("/cart/clear/{username}")
    String clearCart(@PathVariable String username);
}