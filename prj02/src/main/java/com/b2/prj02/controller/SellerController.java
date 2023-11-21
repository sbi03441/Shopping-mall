package com.b2.prj02.controller;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.service.SellerService;
import com.b2.prj02.service.UserService;
import com.b2.prj02.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SellerController {

    private final SellerService sellerService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequestDTO productCreateRequestDTO,
                                           @RequestHeader("X-AUTH-TOKEN") String token) {
        try {
            sellerService.createProduct(productCreateRequestDTO, token);
            return ResponseEntity.ok("상품이 성공적으로 등록되었습니다.");
        } catch (AccessDeniedException e) {
            return handleAccessDeniedException(e);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("판매자 권한이 없습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 잘못되었습니다");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
