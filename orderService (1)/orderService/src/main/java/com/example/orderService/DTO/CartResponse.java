package com.example.orderService.DTO;




import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse
{

    private Long cartId;
    private List<CartItemResponse> items;
    private double totalAmount;

}

