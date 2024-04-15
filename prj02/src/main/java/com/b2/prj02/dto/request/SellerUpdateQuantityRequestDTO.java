package com.b2.prj02.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SellerUpdateQuantityRequestDTO {
    private Long productId;
    private int productQuantity;


    public SellerUpdateQuantityRequestDTO(Long productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

}
