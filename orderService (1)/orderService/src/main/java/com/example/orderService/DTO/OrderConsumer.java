package com.example.orderService.DTO;

import com.example.orderService.entity.Order;
import com.example.orderService.entity.OrderStatus;
import com.example.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "stock-reserved-topic",
            groupId = "order-group"
    )
    public void stockReserved(StockReservedEvent event) {

        Order order = orderRepository
                .findById(event.getOrderId())
                .orElseThrow();

        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        System.out.println(
                "Order Confirmed : " + event.getOrderId()
        );
    }

    @KafkaListener(
            topics = "stock-rejected-topic",
            groupId = "order-group"
    )
    public void stockRejected(StockRejectedEvent event) {

        Order order = orderRepository
                .findById(event.getOrderId())
                .orElseThrow();

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        System.out.println(
                "Order Cancelled : " + event.getOrderId()
        );
    }
}
