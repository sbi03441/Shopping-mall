package com.b2.prj02.dto.sellerDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerUpdateQuantityRequestDTO {
    private final Long productId;
    private String img1;
    private String img2;
    private String img3;

    private final Long category;
    private String productName;
    private double price;
    private int productQuantity;
    private String[] option;
    private int updatedProductQuantity;  // 추가: 재고 수정을 위한 필드

    public SellerUpdateQuantityRequestDTO(Long productId, String img1,String img2, String img3, Long category, String productName, double price, int productQuantity, String[] option, int updatedProductQuantity) {
        this.productId = productId;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
        this.option = option;  // 예: "s,m,l,xl" -> ["s", "m", "l", "xl"]
        this.updatedProductQuantity = updatedProductQuantity;
    }
}
