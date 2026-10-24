package com.example.orderService.client;

import com.example.orderService.DTO.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/auth/{email}")
    UserResponse findUserByEmail(
            @PathVariable("email") String email
    );
}