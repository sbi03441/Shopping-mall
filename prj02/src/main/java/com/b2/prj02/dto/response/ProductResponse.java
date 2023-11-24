//package com.b2.prj02.dto.response;
//
//import com.b2.prj02.entity.product.ProductEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ProductResponse {
//    private Long productId;
//    private Double price;
//    private String option;
//
//    public ProductResponse from(ProductEntity product){
//        return ProductResponse.builder()
//                .productId(product.getProductId())
//                .price(product.getPrice())
//                .option(product.getOption().toString())
//                .build();
//    }
//}
