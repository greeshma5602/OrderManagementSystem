package com.example.inventoryService.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    private Long productId;

    private Integer availableQuantity;
}
