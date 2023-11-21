package com.b2.prj02.entity.product;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private CategoryEntity categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User userId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "product_quantity")
    private int productQuantity;


    @Column(name = "register_date")
    private LocalDateTime registerDate; //

    @Temporal(TemporalType.DATE)
    @Column(name = "sale_enddate")//sale_end_date로 바꿔야할 듯.
    private Date saleEndDate; //

    @Column(name = "product_detail")
    private String productDetail;

    private String img1;
    private String img2;
    private String img3;
    private String option;


    @Builder
    public ProductEntity(Long productId, CategoryEntity categoryId, User userId, String productName, double price, int productQuantity, LocalDateTime registerDate, Date saleEndDate, String productDetail, String img1, String img2, String img3, String option) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.userId = userId;
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

    //DTO to Entity
    public static ProductEntity fromDto(ProductDTO product) {
        return ProductEntity.builder()
                .productId(product.getProductId())
                .userId(User.builder().userId(product.getUserId()).build())//
                .categoryId(CategoryEntity.builder().categoryId(product.getCategoryId()).build())//
                .productName(product.getProductName())
                .price(product.getPrice())
                .productQuantity(product.getProductQuantity())
                .registerDate(product.getRegDate().toLocalDateTime())//
                .saleEndDate(product.getEndDate())
                .productDetail(product.getDetail())
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
                .categoryId(categoryId.getCategoryId())
                .productName(productName)
                .price(price)
                .productQuantity(productQuantity)
                .regDate(Timestamp.valueOf(registerDate))
                .endDate((Timestamp) saleEndDate)
                .detail(productDetail)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .option(option)
                .build();
    }

}