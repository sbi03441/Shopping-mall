package com.b2.prj02.controller;

import com.b2.prj02.dto.ProductDTO;
import com.b2.prj02.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SellerController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        try {
            // 판매 등록 로직 호출
            productService.createProduct(productDTO);
            return ResponseEntity.ok("물품이 성공적으로 등록되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("판매자 권한이 없습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 잘못되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}
