package com.b2.prj02.entity.product;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private CategoryEntity category;

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
    private LocalDateTime registerDate;

    @Column(name = "sale_enddate")
    private LocalDateTime saleEndDate;

    @Column(name = "product_detail")
    private String productDetail;

    @Column(name = "img1")
    private String img1;
    @Column(name = "img2")
    private String img2;
    @Column(name = "img3")
    private String img3;

    @Column(name = "option")
    private String option;

}
