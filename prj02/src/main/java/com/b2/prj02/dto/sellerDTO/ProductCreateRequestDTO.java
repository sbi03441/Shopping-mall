package com.b2.prj02.dto.sellerDTO;

import com.b2.prj02.entity.CategoryEntity;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDTO {
    private CategoryEntity category;
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
