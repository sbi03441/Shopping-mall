package com.b2.prj02.dto.response;

import com.b2.prj02.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartListResponseDTO {

    private Long cartId;
    private Long totalAmount;
    private Long totalPrice;
    private Long totalQuantity;

    public static ShoppingCartListResponseDTO from(ShoppingCart shoppingCart){
        return ShoppingCartListResponseDTO.builder()
                .cartId(shoppingCart.getCartId())
                .totalAmount(shoppingCart.getTotalAmount())
                .totalPrice(shoppingCart.getTotalPrice())
                .totalQuantity(shoppingCart.getTotalQuantity())
                .build();
    }

}
