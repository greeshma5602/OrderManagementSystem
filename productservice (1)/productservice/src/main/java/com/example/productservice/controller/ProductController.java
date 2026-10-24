package com.example.productservice.controller;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @PostMapping
  public ResponseEntity<ProductResponse> addProduct(
          @Valid @RequestBody ProductRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.addProduct(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProduct(
          @PathVariable Long id) {

    return ResponseEntity.ok(
            productService.getProduct(id));
  }

  @PutMapping("/{quantity}/stock")
  public ResponseEntity<ProductResponse> updateStock(
          @PathVariable Integer quantity,
          @RequestParam String name) {

    return ResponseEntity.ok(
            productService.updateProductStock(quantity, name));
  }

  @DeleteMapping("/{id}")
  public String deleteProduct(
          @PathVariable Long id) {

    return
            productService.deleteProduct(id);
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {

    return ResponseEntity.ok(
            productService.getAllProducts());
  }


}