package com.b2.prj02.dto;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import com.sun.istack.NotNull;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    private Long productId;

    @NotNull
    private CategoryEntity category;

    @NotNull
    private User userId;

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
    private String[] option;



    // Format LocalDateTime to String using "yyyy-MM-dd" pattern
    public String getFormattedRegisterDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return registerDate.format(formatter);
    }

    // Format Date to String using "yyyy-MM-dd" pattern
    public String getFormattedSaleEndDate() {
        if (saleEndDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(saleEndDate);
        }
        return null;
    }

}
