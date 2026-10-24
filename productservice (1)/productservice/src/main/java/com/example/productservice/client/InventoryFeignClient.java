package com.example.productservice.client;

import com.example.productservice.dto.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryFeignClient {

    @PostMapping("/inventory")
    void addInventory(
            @RequestBody InventoryRequest request);
    @GetMapping("/inventory/{productId}")
    public InventoryRequest getInventory(
            @PathVariable Long productId);
    @PutMapping("/inventory/{productId}")
    public InventoryRequest updateInventory(
            @PathVariable Long productId,@RequestParam Integer quantity);
    @DeleteMapping("/inventory/{productId}")
    public void deleteInventory(
            @PathVariable Long productId);
}
