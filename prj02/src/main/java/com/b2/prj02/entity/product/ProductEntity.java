package com.b2.prj02.entity.product;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Long productId;

    @ManyToOne    // (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_idx")
    private CategoryEntity category;


    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User userId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "product_Quantity")
    private Integer productQuantity;

    @Column(name = "register_date")
    private LocalDate registerDate;

    @Column(name = "sale_end_date")
    private LocalDate saleEndDate;

    @Column(name = "product_detail")
    private String productDetail;

    @Column(name = "img1")
    private String img1;
    @Column(name = "img2")
    private String img2;
    @Column(name = "img3")
    private String img3;

    @ElementCollection
    @CollectionTable(name = "product_options", joinColumns = @JoinColumn(name = "product_idx"))
    private List<String> option;


    //DTO to Entity
    public static ProductEntity fromDto(ProductDTO product) {
        return ProductEntity.builder()
                .productId(product.getProductId())
                .userId(User.builder().userId(product.getUserId()).build())
                .category(CategoryEntity.builder().category(product.getCategory()).build())
                .productName(product.getProductName())
                .price(product.getPrice())
                .productQuantity(product.getProductQuantity())
                .registerDate(LocalDate.parse((CharSequence) product.getRegisterDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .saleEndDate(LocalDate.parse((CharSequence) product.getSaleEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .productDetail(product.getProductDetail())
                .img1(product.getImg1())
                .img2(product.getImg2())
                .img3(product.getImg3())
                .option(product.getOption())
                .build();
    }

    //Entity to DTO
    public ProductDTO toDto() {
        return ProductDTO.builder()
                .productId(productId)
                .userId(userId.getUserId())
                .category(category.getCategory())
                .productName(productName)
                .price(price)
                .productQuantity(productQuantity)
                .registerDate(Timestamp.valueOf(registerDate.atStartOfDay()))
                .saleEndDate(Timestamp.valueOf(saleEndDate.atStartOfDay()))
                .productDetail(productDetail)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .option(option)
                .build();
    }


}

