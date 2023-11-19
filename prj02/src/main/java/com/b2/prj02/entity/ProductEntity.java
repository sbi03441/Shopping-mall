package com.b2.prj02.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "category_idx")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User userId;

    @Column(name = "product_name")
    private String productName;

    private double price;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Temporal(TemporalType.DATE)
    @NonNull
    @Column(name = "sale_enddate")
    private Date saleEndDate;

    @Column(name = "product_detail")
    private String productDetail;

    private String img1;
    private String img2;
    private String img3;

    private String option;

    public Long getId() {
        return this.productId;
    }
}
