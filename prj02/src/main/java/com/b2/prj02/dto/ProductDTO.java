package com.b2.prj02.dto;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    private Long productId;

    private String productName;
    private double price;
    private int productQuantity;

    @NotNull
    private LocalDateTime registerDate;

    @NotNull
    private Date saleEndDate;

    private String productDetail;

    private String img1;
    private String img2;
    private String img3;
    private String option;

    @NotNull
    private CategoryEntity category;

    @NotNull
    private User userId;

}
