package com.b2.prj02.controller;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerProductRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.ProductEntity;
import com.b2.prj02.service.ProductService;
import com.b2.prj02.service.UserService;
import com.b2.prj02.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SellerController {

    private final ProductService productService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequestDTO productCreateRequestDTO,
                                           @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            productService.createProduct(productCreateRequestDTO, token);
            return ResponseEntity.ok("상품이 성공적으로 등록되었습니다.");
        } catch (AccessDeniedException e) {
            return handleAccessDeniedException(e);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @GetMapping("/{userId}/active")
    public ResponseEntity<String> getActiveProducts(@PathVariable Long userId,
                                                    @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            List<SellerProductRequestDTO> activeProducts = productService.getActiveProducts(userId, userEmail);

            if (activeProducts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 물품이 없습니다.");
            } else {
                return ResponseEntity.ok(activeProducts.toString());
            }
        } catch (AccessDeniedException e) {
            return handleAccessDeniedException(e);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long productId,
                                                        @RequestBody SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO,
                                                        @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);

            sellerUpdateQuantityRequestDTO.setProductId(productId);
            ProductEntity updatedProduct = productService.updateProductQuantity(sellerUpdateQuantityRequestDTO, userEmail);
            return ResponseEntity.ok("물품 재고가 성공적으로 수정되었습니다. 업데이트된 물품 ID: " + updatedProduct.getId());
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @GetMapping("/{userId}/sold")
    public ResponseEntity<String> getSoldProducts(@PathVariable Long userId,
                                                  @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);

            List<SellerProductRequestDTO> soldProducts = productService.getSoldProducts(userId, userEmail);

            if (soldProducts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 물품이 없습니다.");
            } else {
                return ResponseEntity.ok(soldProducts.toString());
            }
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("판매자 권한이 없습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 잘못되었습니다");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
