package com.b2.prj02.dto.sellerDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SellerUpdateQuantityRequestDTO {
    private Long productId;
    private Long category;
    private String productName;
    private double price;
    private int productQuantity;
    private String img1;
    private String img2;
    private String img3;
    private List<String> option;


    public SellerUpdateQuantityRequestDTO(Long productId, Long category, String productName, double price, int productQuantity,  String img1,String img2, String img3,List<String> option) {
        this.productId = productId;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.option = option;
    }

}
