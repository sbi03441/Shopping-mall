//package com.b2.prj02.controller.product;
//
//import com.b2.prj02.dto.product.ProductDTO;
//import com.b2.prj02.exception.product.ProductNotFoundException;
//import com.b2.prj02.service.product.ProductService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@Slf4j
//@RestController
//@RequestMapping("/api/shop")
//@RequiredArgsConstructor
//public class ProductController {
//
//    private final ProductService productService;
//
//    //상품 전체 조회
//
//
//
//
//    //상품 상세 조회
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
//        try {
//            ProductDTO productDTO = productService.getProductById(productId);
//            return ResponseEntity.ok().body(productDTO);
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//
//
//
//}
