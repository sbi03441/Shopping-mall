package com.b2.prj02.entity;

import com.b2.prj02.entity.product.ProductEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "category")
public class CategoryEntity {

    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx")
    private Long category;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> productList;

    // 기본 생성자 추가
    public CategoryEntity() {
    }

    // 인자를 받는 생성자 추가
    public CategoryEntity(Long category, String categoryName, List<ProductEntity> productList) {
        this.category = category;
        this.categoryName = categoryName;
        this.productList = productList;
    }


}
