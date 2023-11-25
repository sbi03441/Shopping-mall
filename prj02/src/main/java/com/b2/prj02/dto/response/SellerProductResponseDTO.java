package com.b2.prj02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SellerProductResponseDTO {

    private Long productId;
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
