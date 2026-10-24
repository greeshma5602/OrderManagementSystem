package com.example.productservice.service;

import com.example.productservice.client.InventoryFeignClient;
import com.example.productservice.dto.request.InventoryRequest;
import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.exceptions.NotFoundException;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryFeignClient inventoryFeignClient;



    public ProductResponse addProduct(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());



        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        Product savedProduct = productRepository.save(product);
        inventoryFeignClient.addInventory(new InventoryRequest(savedProduct.getId(), request.getStock()));


        return mapToResponse(savedProduct);
    }


    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new
                        NotFoundException("Product"));

        return mapToResponse(product);
    }


    public ProductResponse updateProductStock(Integer stock, String name) {

        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Product"));

        inventoryFeignClient.updateInventory(product.getId(), stock);

        return mapToResponse(product);
    }


    public String deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product "));


        inventoryFeignClient.deleteInventory(product.getId());
        productRepository.delete(product);
        return "Product deleted successfully";
    }


    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse mapToResponse(Product product) {
        InventoryRequest req  =inventoryFeignClient.getInventory(product.getId());
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                req.getAvailableQuantity(),
                product.getDescription()
        );
    }




}


