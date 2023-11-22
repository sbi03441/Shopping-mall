package com.b2.prj02.dto.product;

import com.b2.prj02.entity.product.ProductEntity;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;


@Getter
@Builder
public class ProductDTO {

    private final Long productId;
    private final Long categoryId;
    private final Long userId;
    private String productName;
    private double price;
    private int productQuantity;
    private Timestamp regDate;
    private Timestamp endDate;
    private String detail;
    private String img1;
    private String img2;
    private String img3;
    private List<String> option;

    @Builder
    public ProductDTO(Long productId, Long categoryId, Long userId, String productName, double price, int productQuantity, Timestamp regDate, Timestamp endDate, String detail, String img1, String img2, String img3, List<String> option) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
        this.regDate = regDate;
        this.endDate = endDate;
        this.detail = detail;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.option = option;
    }


    //Entity to DTO
    public static ProductDTO toProductDTO(ProductEntity product){
        return ProductDTO.builder()
                .productId(product.getProductId())
                //.categoryId(product.getCategoryId().getCategoryId())//categoryEntity의 id값 받아오기
                .categoryId(product.getProductId())
                .userId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .productQuantity(product.getProductQuantity())
                .regDate(Timestamp.valueOf(product.getRegisterDate()))
                .endDate(Timestamp.valueOf(product.getSaleEndDate()))
                .detail(product.getProductDetail())
                .img1(product.getImg1())
                .img2(product.getImg2())
                .img3(product.getImg3())
                .option(Collections.singletonList(product.getOption()))
                .build();
    }


}
