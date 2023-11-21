package com.b2.prj02.dto.sellerDTO;

import com.b2.prj02.entity.CategoryEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductCreateRequestDTO {
    private final Long category;
    private String productName;
    private double price;
    private int productQuantity;
    private LocalDateTime registerDate;
    private LocalDateTime saleEndDate;
    private String productDetail;
    private String img1;
    private String img2;
    private String img3;
    private String[] option;
}
