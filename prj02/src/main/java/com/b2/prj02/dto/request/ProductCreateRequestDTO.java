package com.b2.prj02.dto.request;

import com.b2.prj02.entity.CategoryEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductCreateRequestDTO {

    private Long category;
    private String productName;
    private double price;
    private int productQuantity;
    private LocalDate registerDate;
    private LocalDate saleEndDate;
    private String productDetail;
    private String img1;
    private String img2;
    private String img3;
    private List<String> option;

}
