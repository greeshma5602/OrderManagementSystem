package com.example.orderService.client;

import com.example.orderService.DTO.ProductResponse;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE", path = "/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProduct(
            @PathVariable Long id);

    @PutMapping("/{productId}/reduce-stock")
    ProductResponse reduceStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity);

    @PutMapping("/{quantity}/add-stock")
    ProductResponse addStock(
            @PathVariable Integer quantity,
            @RequestParam Long id);
}
