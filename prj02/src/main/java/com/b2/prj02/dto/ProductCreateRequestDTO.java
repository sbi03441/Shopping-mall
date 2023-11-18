package com.b2.prj02.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDTO {
    private String productName;
    private double price;
    private int productQuantity;
    private LocalDateTime registerDate;
    private Date saleEndDate;
    private String productDetail;
    private String img1;
    private String img2;
    private String img3;
    private String[] option;
    private Long userId;
}
