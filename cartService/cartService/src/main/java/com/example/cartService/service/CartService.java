package com.example.cartService.service;

import com.example.cartService.client.InventoryFeignClient;
import com.example.cartService.client.ProductClient;
import com.example.cartService.dto.*;
import com.example.cartService.entity.Cart;
import com.example.cartService.entity.CartItem;
import com.example.cartService.exceptions.InvalidQuantityException;
import com.example.cartService.exceptions.NotFoundException;
import com.example.cartService.repository.CartItemRepository;
import com.example.cartService.repository.CartRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;
    private final CartItemRepository cartItemRepository;
    private final InventoryFeignClient inventoryFeignClient;

    public CartService(
            CartRepository cartRepository,
            ProductClient productClient,
            CartItemRepository cartItemRepository,InventoryFeignClient inventoryFeignClient) {

        this.cartRepository = cartRepository;
        this.productClient = productClient;
        this.cartItemRepository = cartItemRepository;
        this.inventoryFeignClient = inventoryFeignClient;
    }
    @Transactional
    public void clearCart(String username) {

        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }
    @Transactional
    public CartResponse addToCart(
            String username,
            CartRequest request) {

        if (request.getQuantity() == null ||
                request.getQuantity() <= 0) {

            throw new InvalidQuantityException(
                    "Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUsername(username);
                    return cartRepository.save(newCart);
                });


        ProductResponse product;

        try {
            product = productClient.findProductById(
                    request.getProductId());

        } catch (FeignException.NotFound ex) {
            throw new NotFoundException("Product");
        }
        InventoryRequest inventory =
                inventoryFeignClient.getInventory(product.getId());

        int availableQty = inventory.getAvailableQuantity();
        CartItem item = cartItemRepository
                .findByCartAndProductid(
                        cart,
                        product.getId())
                .orElse(null);

        if (item != null) {
            Integer totalRequestedQty =
                    item.getQuantity() + request.getQuantity();

            if (totalRequestedQty > availableQty) {
                throw new InvalidQuantityException(
                        "Insufficient stock available");
            }

            item.setQuantity(totalRequestedQty); }
        else {

            if (request.getQuantity() > availableQty) {
                throw new InvalidQuantityException(
                        "Insufficient stock available");
            }

            item = new CartItem();
            item.setProductid(product.getId());
            item.setQuantity(request.getQuantity());

            cart.addItem(item);
        }

        cartItemRepository.save(item);

        return mapToResponse(cart);
    }

    public CartResponse getCart(String username) {

        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart"));

        return mapToResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItem(
            String username,
            Long productId,
            Integer quantity) {

        if (quantity == null || quantity < 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than or equal to 0");
        }
        InventoryRequest inventory =
                inventoryFeignClient.getInventory(productId);

        if (quantity > inventory.getAvailableQuantity()) {
            throw new InvalidQuantityException(
                    "Insufficient stock available");
        }

        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart"));

        CartItem item = cartItemRepository
                .findByCartAndProductid(
                        cart,
                        productId)
                .orElseThrow(() ->
                        new NotFoundException("CartItem"));

        if (quantity == 0) {

            cart.removeItem(item);

        } else {

            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return mapToResponse(cart);
    }

    @Transactional
    public CartResponse removeFromCart(
            String username,
            Long productId) {

        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Cart"));

        CartItem item = cartItemRepository
                .findByCartAndProductid(
                        cart,
                        productId)
                .orElseThrow(() ->
                        new NotFoundException("CartItem"));

        cart.removeItem(item);

        return mapToResponse(cart);
    }

    private CartResponse mapToResponse(Cart cart) {

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(ci -> {

                            ProductResponse product =
                                    productClient.findProductById(
                                            ci.getProductid());

                            return new CartItemResponse(
                                    product.getId(),
                                    product.getName(),
                                    product.getPrice(),
                                    ci.getQuantity()
                            );
                        })
                        .toList();

        double total = items.stream()
                .mapToDouble(item ->
                        item.getPrice() * item.getQuantity())
                .sum();

        return new CartResponse(
                cart.getId(),
                items,
                total
        );
    }
}