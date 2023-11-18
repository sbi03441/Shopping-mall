package com.b2.prj02.controller;

import com.b2.prj02.dto.ProductDTO;import com.b2.prj02.dto.SellerProductResponseDTO;
import com.b2.prj02.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/active")
    public ResponseEntity<String> getActiveProducts() {
        try {
            // 물품 조회 로직 호출
            List<SellerProductResponseDTO> activeProducts = productService.getActiveProducts();

            if (activeProducts.isEmpty()) {
                return ResponseEntity.ok("조회된 물품이 없습니다.");
            } else {
                return ResponseEntity.ok(activeProducts.toString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    @PostMapping("/{productId}/quantity")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long productId, @RequestBody SellerProductResponseDTO sellerProductResponseDTO) {
        try {
            sellerProductResponseDTO.setProductId(productId);
            productService.updateProductQuantity(sellerProductResponseDTO);
            return ResponseEntity.ok("물품 재고가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 잘못되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}
