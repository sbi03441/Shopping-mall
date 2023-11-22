package com.b2.prj02.service;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.mapper.ProductMapper;
import com.b2.prj02.repository.ProductRepository;
import com.b2.prj02.repository.UserRepository;
import com.b2.prj02.role.UserStatus;
import com.b2.prj02.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 쇼핑몰 판매 물품 등록
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createProduct(ProductCreateRequestDTO productCreateRequestDTO, String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String userEmail = jwtTokenProvider.getUserEmail(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AccessDeniedException("사용자를 찾을 수 없습니다."));

        if (user.getStatus() != UserStatus.SELLER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("SELLER 권한이 없습니다.");
        }

        try {
            // ProductMapper를 사용하여 DTO를 Entity로 변환
            ProductEntity productEntity = ProductMapper.INSTANCE.toProductEntity(productCreateRequestDTO);
            // 추가로 user 설정
            productEntity.setUserId(user);
            ProductEntity savedProduct = productRepository.save(productEntity);

            return ResponseEntity.status(200).body("상품이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.", e);
        }
    }
    // 판매 물품 조회
    public List<ProductCreateRequestDTO> getActiveProducts(Long userId, String userEmail) {
        LocalDateTime currentDate = LocalDateTime.now();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> activeProducts = productRepository.findByUserIdAndSaleEndDateBefore(user, currentDate);
        return activeProducts.stream()
                .map(ProductMapper.INSTANCE::toSellerProductDTO)
                .collect(Collectors.toList());
    }

    // 재고 수정
    public ProductEntity updateProductQuantity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO, String userEmail) {
        ProductEntity productEntity = productRepository.findById(sellerUpdateQuantityRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("물품을 찾을 수 없습니다."));

        if (!productEntity.getUserId().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("해당 물품의 판매자가 아닙니다.");
        }

        // 재고 수정 로직 추가
        int updatedQuantity = sellerUpdateQuantityRequestDTO.getUpdatedProductQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("재고는 음수가 될 수 없습니다.");
        }

        // ProductMapper를 사용하여 Entity 업데이트
        return ProductMapper.INSTANCE.updateEntity(sellerUpdateQuantityRequestDTO, productEntity);

    }
    // 판매 종료된 물품 조회
    public List<ProductCreateRequestDTO> getSoldProducts(Long userId, String userEmail) {
        // 현재 날짜를 가져오는 로직을 추가.
        LocalDateTime currentDate = LocalDateTime.now();

        // 사용자 이메일을 사용하여 사용자 정보 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> soldProducts = productRepository.findByUserIdAndSaleEndDateAfter(user, currentDate);

        // ProductMapper를 사용하여 Entity를 DTO로 변환
        return soldProducts.stream()
                .map(ProductMapper.INSTANCE::toSellerProductDTO)
                .collect(Collectors.toList());

    }
}
