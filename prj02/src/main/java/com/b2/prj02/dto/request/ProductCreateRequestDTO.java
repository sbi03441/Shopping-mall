package com.b2.prj02.dto.request;

import com.b2.prj02.entity.CategoryEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
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
    @SuppressWarnings("unused")  // Lombok이 생성하는 생성자를 사용하기 위해 경고 억제
    public ProductCreateRequestDTO() {
    }
    public ProductCreateRequestDTO(Long category, String productName, double price, int productQuantity,
                                   LocalDate registerDate, LocalDate saleEndDate, String productDetail,
                                   String img1, String img2, String img3, List<String> option) {
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

    public Timestamp convertRegisterDateToTimestamp() {
        return Timestamp.valueOf(registerDate.atStartOfDay());
    }

    public Timestamp convertSaleEndDateToTimestamp() {
        return Timestamp.valueOf(saleEndDate.atStartOfDay());
    }
}
