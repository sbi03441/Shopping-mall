package com.b2.prj02.entity.product;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class ProductEntity {

    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Long productId;

    @ManyToOne(cascade = CascadeType.PERSIST)
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
    private LocalDateTime registerDate;

    @Column(name = "sale_end_date")
    private LocalDateTime saleEndDate;

    @Column(name = "product_detail")
    private String productDetail;

    @Column(name = "img1")
    private String img1;
    @Column(name = "img2")
    private String img2;
    @Column(name = "img3")
    private String img3;

    @JoinColumn(name = "option_idx")
    private String option;

    // getOption 메서드 수정
    public String[] getOptionArray() {
        return option != null ? option.split(",") : new String[0];
    }

}
