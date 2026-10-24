package com.example.cartService.client;

import com.example.cartService.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "PRODUCT-SERVICE"
)
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse findProductById(@PathVariable Long id);

}
