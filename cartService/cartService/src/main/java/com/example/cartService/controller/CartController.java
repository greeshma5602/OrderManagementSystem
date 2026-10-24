package com.example.cartService.controller;

import com.example.cartService.dto.CartRequest;
import com.example.cartService.dto.CartResponse;
import com.example.cartService.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            @Valid @RequestBody CartRequest request,
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                cartService.addToCart(username, request)
        );
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                cartService.getCart(username)
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long productId,
            @RequestParam Integer quantity,
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        username,
                        productId,
                        quantity)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartResponse> removeFromCart(
            @PathVariable Long productId,
            @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(
                cartService.removeFromCart(
                        username,
                        productId)
        );
    }
    @DeleteMapping("/clear/{username}")
    public ResponseEntity<String> clearCart(
            @PathVariable String username) {

        cartService.clearCart(username);
        return ResponseEntity.ok("Cart cleared");
    }
}