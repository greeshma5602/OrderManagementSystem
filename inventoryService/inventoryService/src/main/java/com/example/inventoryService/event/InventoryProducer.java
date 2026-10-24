package com.example.inventoryService.event;

import com.example.events.StockRejectedEvent;
import com.example.events.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryProducer {

    private final KafkaTemplate<String, Object>
            kafkaTemplate;

    public void publishStockReserved(
            StockReservedEvent event) {

        kafkaTemplate.send(
                "stock-reserved-topic",
                event
        );
    }

    public void publishStockRejected(
            StockRejectedEvent event) {

        kafkaTemplate.send(
                "stock-rejected-topic",
                event
        );
    }
}

