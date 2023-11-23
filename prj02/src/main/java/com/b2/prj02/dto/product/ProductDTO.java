package com.b2.prj02.dto.product;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.entity.product.ProductEntity;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;


@Getter
@Builder
public class ProductDTO {

    private final Long productId;
    private final CategoryEntity category;
    private final User userId;
    private String productName;
    private double price;
    private int productQuantity;
    private Timestamp registerDate;
    private Timestamp saleEndDate;
    private String productDetail;
    private String img1;
    private String img2;
    private String img3;
    private List<String> option;

    @Builder
    public ProductDTO(Long productId, CategoryEntity category, User userId, String productName, double price, int productQuantity, Timestamp registerDate, Timestamp saleEndDate, String productDetail, String img1, String img2, String img3, List<String> option) {
        this.productId = productId;
        this.category = category;
        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
        this.registerDate = registerDate;
        this.saleEndDate = saleEndDate;
        this.productDetail = productDetail;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.option = option;
    }


    //Entity to DTO
    public static ProductDTO toProductDTO(ProductEntity product){
        return ProductDTO.builder()
                .productId(product.getProductId())
                .category(product.getCategory())
                .userId(product.getUserId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .productQuantity(product.getProductQuantity())
                .registerDate(Timestamp.valueOf(String.valueOf(product.getRegisterDate())))
                .saleEndDate(Timestamp.valueOf(String.valueOf(product.getSaleEndDate())))
                .productDetail(product.getProductDetail())
                .img1(product.getImg1())
                .img2(product.getImg2())
                .img3(product.getImg3())
                .option(product.getOption())
                .build();
    }


}

