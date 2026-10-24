package com.example.inventoryService.service;

import com.example.inventoryService.entity.Inventory;
import com.example.inventoryService.exceptions.NotFoundException;
import com.example.inventoryService.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository ;

    public boolean reserveStock(Long productId, Integer quantity) {

        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElse(null);

        if (inventory == null) {
            return false;
        }

        if (inventory.getAvailableQuantity() < quantity) {
            return false;
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity
        );

        inventoryRepository.save(inventory);

        return true;
    }

    public void addInventory(Inventory inventory) {
         inventoryRepository.save(inventory);
    }

    public Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new NotFoundException("Inventory  not found"));
    }
    public Inventory updateInventory(Long productId, Integer quantity) {
      Inventory inventory   =inventoryRepository.findByProductId(productId).get();
        inventory .setAvailableQuantity(quantity);
            return inventoryRepository.save(inventory);
    }
    @Transactional
    public void deleteInventory(
            @PathVariable Long productId) {

        inventoryRepository.deleteByProductId(productId);
    }

}