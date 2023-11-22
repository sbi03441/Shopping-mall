package com.b2.prj02.dto.sellerDTO;

import com.b2.prj02.entity.CategoryEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class ProductCreateRequestDTO {
    @Getter
    private Long category;
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
    @SuppressWarnings("unused")  // Lombok이 생성하는 생성자를 사용하기 위해 경고 억제
    public ProductCreateRequestDTO() {
    }
    public ProductCreateRequestDTO(Long category, String productName, double price, int productQuantity,
                                   LocalDateTime registerDate, LocalDateTime saleEndDate, String productDetail,
                                   String img1, String img2, String img3, String[] option) {
        this.category = category;
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
}
