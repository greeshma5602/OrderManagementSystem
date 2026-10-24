package com.example.inventoryService.controller;
import com.example.inventoryService.entity.Inventory;
import com.example.inventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public void addInventory(
            @RequestBody Inventory inventory) {

         inventoryService.addInventory(inventory);
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(
            @PathVariable Long productId) {

        return inventoryService.getInventory(productId);
    }
    @PutMapping("/{productId}")
    public Inventory updateInventory(
            @PathVariable Long productId,@RequestParam Integer quantity) {

        return inventoryService.updateInventory(productId,quantity);
    }
    @DeleteMapping("/{productId}")
    public void deleteInventory(
            @PathVariable Long productId) {

        inventoryService.deleteInventory(productId);
    }
}
