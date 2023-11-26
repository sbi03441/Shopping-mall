package com.b2.prj02.dto.response;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartResponseDTO {

    private Long cartId; // 장바구니 번호
    private UserResponseDTO user; // 유저
    private ProductDTO product; // 상품
    private Long amount; // 수량

    public static ShoppingCartResponseDTO from(ShoppingCart shoppingCart){
        return ShoppingCartResponseDTO.builder()
                .cartId(shoppingCart.getCartId())
                .user(UserResponseDTO.from(shoppingCart.getUserId()))
                .product(ProductDTO.from(shoppingCart.getProductId()))
                .amount(shoppingCart.getAmount())
                .build();
    }
}
