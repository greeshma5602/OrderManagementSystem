package com.example.inventoryService.event;


import com.example.events.OrderCreatedEvent;
import com.example.events.OrderItemEvent;
import com.example.events.StockRejectedEvent;
import com.example.events.StockReservedEvent;
import com.example.inventoryService.entity.Inventory;
import com.example.inventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;
    private final InventoryProducer inventoryProducer;

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "inventory-group-v2"
    )
    public void consume(OrderCreatedEvent event) {

        boolean stockAvailable = true;

        // First validate all items
        for (OrderItemEvent item : event.getItems()) {

            Inventory inventory = inventoryService.getInventory(
                    Long.valueOf(item.getProductId())
            );

            if (inventory.getAvailableQuantity() < item.getQuantity()) {
                stockAvailable = false;
                break;
            }
        }

        // If stock available reduce inventory
        if (stockAvailable) {

            for (OrderItemEvent item : event.getItems()) {

                inventoryService.reserveStock(
                        Long.valueOf(item.getProductId()),
                        item.getQuantity()
                );
            }

            System.out.println(
                    "Inventory Reserved For Order : "
                            + event.getOrderId()
            );

            inventoryProducer.publishStockReserved(
                    new StockReservedEvent(
                            event.getOrderId()
                    )
            );

        } else {

            System.out.println(
                    "Out Of Stock For Order : "
                            + event.getOrderId()
            );

            inventoryProducer.publishStockRejected(
                    new StockRejectedEvent(
                            event.getOrderId(),
                            "Insufficient Stock"
                    )
            );
        }
    }
}