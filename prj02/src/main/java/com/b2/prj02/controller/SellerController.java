package com.b2.prj02.controller;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.dto.request.ProductCreateRequestDTO;
import com.b2.prj02.dto.request.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.dto.response.SellerProductResponseDTO;
import com.b2.prj02.service.SellerService;
import com.b2.prj02.user.entity.User;
import com.b2.prj02.user.repository.UserRepository;
import com.b2.prj02.user.service.UserService;
import com.b2.prj02.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequestDTO productCreateRequestDTO,
                                           @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            sellerService.createProduct(productCreateRequestDTO, token);
            return ResponseEntity.ok("상품이 성공적으로 등록되었습니다.");
        } catch (AccessDeniedException e) {
            return handleAccessDeniedException(e);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품 등록 중 오류가 발생했습니다.");
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveProducts(@RequestHeader("X-AUTH-TOKEN") String token) {

        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));
            List<SellerProductResponseDTO> activeProducts = sellerService.getActiveProducts(user);

            return ResponseEntity.ok(activeProducts);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    @PutMapping("/quantity")
    public ResponseEntity<?> updateProductQuantity(@RequestBody SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO,
                                                   @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            ProductDTO updatedProduct = sellerService.updateProductQuantity(sellerUpdateQuantityRequestDTO, userEmail);
            return ResponseEntity.ok("물품 재고가 성공적으로 수정되었습니다. 업데이트된 물품 ID: " + updatedProduct.getProductId());
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }
    @GetMapping("/sold")
    public ResponseEntity<?> getSoldProducts(@RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));
            List<SellerProductResponseDTO> soldProducts = sellerService.getSoldProducts(user);


            return ResponseEntity.ok(soldProducts);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access Denied Exception", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("판매자 권한이 없습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Illegal Argument Exception", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 잘못되었습니다");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception e) {
        log.error("Internal Server Error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
