package com.example.cartService.repository;


import com.example.cartService.entity.Cart;
import com.example.cartService.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    Optional<CartItem> findByCartAndProductid(Cart cart, Long productid);

    void deleteByCartAndProductid(Cart cart, Long productid);

}
