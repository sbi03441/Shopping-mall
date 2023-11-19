package com.b2.prj02.dto.sellerDTO;

import com.b2.prj02.dto.CategoryDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerUpdateQuantityRequestDTO {
    private Long productId;
    private String img1;
    private CategoryDTO category;
    private String productName;
    private double price;
    private int productQuantity;
    private String[] option;
    private int updatedProductQuantity;  // 추가: 재고 수정을 위한 필드

    public SellerUpdateQuantityRequestDTO(Long productId, String img1, CategoryDTO category, String productName, double price, int productQuantity, String[] option, int updatedProductQuantity) {
        this.productId = productId;
        this.img1 = img1;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
        this.option = option;  // 예: "s,m,l,xl" -> ["s", "m", "l", "xl"]
        this.updatedProductQuantity = updatedProductQuantity;
    }
}

