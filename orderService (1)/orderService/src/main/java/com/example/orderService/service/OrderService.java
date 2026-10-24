package com.example.orderService.service;

import com.example.orderService.DTO.*;
import com.example.orderService.client.CartClient;
import com.example.orderService.client.ProductClient;
import com.example.orderService.client.UserClient;
import com.example.orderService.entity.Order;
import com.example.orderService.entity.OrderItem;
import com.example.orderService.entity.OrderStatus;
import com.example.orderService.entity.PaymentStatus;
import com.example.orderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartClient cartClient;
    private final UserClient userClient;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;

    @Transactional
    public String placeOrder(String username) {

        CartResponse cartResponse = cartClient.getCart(username);

        if (cartResponse.getItems() == null ||
                cartResponse.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        UserResponse user =
                userClient.findUserByEmail(username);

        Order order = new Order();

        order.setUserId(user.getId());
        order.setStatus(OrderStatus.INVENTORY_CHECK_PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPaymentStatus(PaymentStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;

        for (CartItemResponse cartItem : cartResponse.getItems()) {

           /* ProductResponse response =
                    productClient.getProduct(cartItem.getProductId());

            if (response.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Product " + response.getName() + " is out of stock");
            }

            productClient.reduceStock(
                    cartItem.getProductId(),
                    cartItem.getQuantity());*/

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getPrice());

            total += cartItem.getPrice() * cartItem.getQuantity();

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder =orderRepository.save(order);
        List<OrderItemEvent> items = savedOrder.getItems()
                .stream()
                .map(item -> new OrderItemEvent(
                        item.getProductId(),
                        item.getQuantity()
                ))
                .toList();

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                items
        );

        orderProducer.publishOrderCreatedEvent(event);

        // Clear cart after successful order creation
        cartClient.clearCart(username);

        return "Order Created Successfully";
    }
    @Transactional
    public String cancelOrder(Long orderId,
                              String username) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        UserResponse user =
                userClient.findUserByEmail(username);

        if (!order.getUserId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot cancel this order");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException(
                    "Order already cancelled");
        }

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED) {

            throw new RuntimeException(
                    "Cannot cancel after shipping");
        }

        order.setStatus(OrderStatus.CANCELLED);

        if (order.getPaymentStatus() ==
                PaymentStatus.SUCCESS) {

            order.setPaymentStatus(
                    PaymentStatus.FAILED);
        }
        for (OrderItem item : order.getItems()) {

            productClient.addStock(
                    item.getQuantity(),
                    item.getProductId()
            );
        }
        cartClient.clearCart(username);
        orderRepository.save(order);

        return "Order Cancelled Successfully";
    }

    public List<OrderResponse> getOrders(String username) {

        UserResponse user =
                userClient.findUserByEmail(username);

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() ==
                OrderStatus.CANCELLED) {

            throw new RuntimeException(
                    "Cannot update cancelled order");
        }

        if (order.getStatus() ==
                OrderStatus.DELIVERED) {

            throw new RuntimeException(
                    "Order already delivered");
        }

        order.setStatus(newStatus);

        if (newStatus == OrderStatus.DELIVERED &&
                order.getPaymentStatus() ==
                        PaymentStatus.PENDING) {

            order.setPaymentStatus(
                    PaymentStatus.SUCCESS);
        }

        orderRepository.save(order);

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(i -> new OrderItemResponse(
                                i.getProductId(),
                                i.getQuantity(),
                                i.getPrice()
                        ))
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getOrderDate(),
                items,
                order.getPaymentStatus()
        );
    }
}