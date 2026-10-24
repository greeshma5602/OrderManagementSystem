package com.example.cartService.client;

import com.example.cartService.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8081"
)
public interface UserClient {
    @GetMapping("/products/{id}")
    ProductResponse getProduct(
            @PathVariable Long id);

}
