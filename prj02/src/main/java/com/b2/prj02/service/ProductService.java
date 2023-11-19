package com.b2.prj02.service;

import com.b2.prj02.dto.*;
import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerProductRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.ProductEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.mapper.ProductMapper;
import com.b2.prj02.repository.ProductRepository;
import com.b2.prj02.repository.UserRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 쇼핑몰 판매 물품 등록
    @Transactional
    public ResponseEntity<?> createProduct(ProductCreateRequestDTO productCreateRequestDTO, String token) {
        // 토큰의 유효성을 검증
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 유효한 토큰인 경우 사용자 이메일 추출
        String userEmail = jwtTokenProvider.getUserEmail(token);

        // 사용자 이메일을 이용하여 사용자 정보 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AccessDeniedException("사용자를 찾을 수 없습니다."));

        try {
            // ProductMapper를 사용하여 DTO를 Entity로 변환
            ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productCreateRequestDTO);
            // 추가로 user 설정
            productEntity.setUserId(user);

            ProductEntity savedProduct = productRepository.save(productEntity);

            return ResponseEntity.status(200).body("상품이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            // 예외가 발생하면 롤백
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.", e);
        }
    }

    private ProductEntity convertToProductEntity(ProductCreateRequestDTO productCreateRequestDTO, User user) {
        return ProductMapper.INSTANCE.toEntity(productCreateRequestDTO);
    }

    // 판매 물품 조회
    public List<SellerProductRequestDTO> getActiveProducts(Long userId, String userEmail) {
        // 현재 날짜를 가져오는 로직을 추가.
        LocalDateTime currentDate = LocalDateTime.now();

        List<ProductEntity> activeProducts = productRepository.findByUserIdAndSaleEndDateAfter(Long.valueOf(userEmail), currentDate);


        return activeProducts.stream()
                .map(ProductMapper.INSTANCE::toSellerProductDTO)
                .collect(Collectors.toList());
    }

    // 재고 수정
    public ProductEntity updateProductQuantity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO, String userEmail) {
        ProductEntity productEntity = productRepository.findById(sellerUpdateQuantityRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("물품을 찾을 수 없습니다."));

        // 유저 이메일이 토큰에서 가져온 이메일과 일치하는지 확인
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
    public List<SellerProductRequestDTO> getSoldProducts(Long userId, String userEmail) {
        // 현재 날짜를 가져오는 로직을 추가.
        LocalDateTime currentDate = LocalDateTime.now();

        List<ProductEntity> soldProducts = productRepository.findByUserIdAndSaleEndDateBefore(Long.valueOf(userEmail), currentDate);

        // ProductMapper를 사용하여 Entity를 DTO로 변환
        return soldProducts.stream()
                .map(ProductMapper.INSTANCE::toSellerProductDTO)
                .collect(Collectors.toList());

    }
}
