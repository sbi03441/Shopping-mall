package com.b2.prj02.dto.product;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Builder
@AllArgsConstructor
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
    private String option;



}
