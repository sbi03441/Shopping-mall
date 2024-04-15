package com.b2.prj02.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {

    private Long productId; // 상품 id
    private Long amount;   // 상품 수량

}
