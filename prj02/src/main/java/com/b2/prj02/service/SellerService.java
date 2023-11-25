package com.b2.prj02.service;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.dto.request.ProductCreateRequestDTO;
import com.b2.prj02.dto.request.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.dto.response.SellerProductResponseDTO;
import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.entity.User;

import com.b2.prj02.repository.CategoryRepository;

import com.b2.prj02.repository.product.ProductRepository;
import com.b2.prj02.repository.UserRepository;
import com.b2.prj02.role.UserStatus;
import com.b2.prj02.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    // 판매 물품 등록
    @Transactional
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
        ProductEntity productEntity = CreateProductEntity(productCreateRequestDTO, user);

        ProductEntity savedProduct = productRepository.save(productEntity);

        // ProductEntity를 ProductDTO로 변환하여 반환
        return ResponseEntity.status(HttpStatus.OK).body(savedProduct.toDto());
    }

    @Transactional
    // 판매 물품 조회
    public List<SellerProductResponseDTO> getActiveProducts(User user) {
        List<ProductEntity> activeProducts = productRepository.findByUserIdAndSaleEndDateAfter(user, LocalDate.now());

        return activeProducts.stream()
                .map(this::createSellerProductDTOFromEntity)
                .collect(Collectors.toList());
    }

    // 재고 수정
    public ProductDTO updateProductQuantity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO, String userEmail) {
        ProductEntity productEntity = productRepository.findById(sellerUpdateQuantityRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("물품을 찾을 수 없습니다."));

        if (!productEntity.getUserId().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("해당 물품의 판매자가 아닙니다.");
        }

        int updatedQuantity = sellerUpdateQuantityRequestDTO.getProductQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("재고는 음수가 될 수 없습니다.");
        }

        productEntity.setProductQuantity(updatedQuantity);

        productRepository.save(productEntity);

        return productEntity.toDto();

    }

    // 판매 종료된 물품 조회
    public List<SellerProductResponseDTO> getSoldProducts(User user) {
        List<ProductEntity> soldProducts = productRepository.findByUserIdAndSaleEndDateBefore(user, LocalDate.now());

        return soldProducts.stream()
                .map(this::createSellerProductDTOFromEntity)
                .collect(Collectors.toList());
    }

    LocalDate now = LocalDate.now();
    // ProductEntity 생성
    private ProductEntity CreateProductEntity(ProductCreateRequestDTO productCreateRequestDTO, User user) {
        CategoryEntity category = categoryRepository.findByCategory(productCreateRequestDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리입니다."));

        return ProductEntity.builder()
                .category(category)
                .productName(productCreateRequestDTO.getProductName())
                .price(productCreateRequestDTO.getPrice())
                .productQuantity(productCreateRequestDTO.getProductQuantity())
                .registerDate(now)
                .saleEndDate(productCreateRequestDTO.getSaleEndDate())
                .productDetail(productCreateRequestDTO.getProductDetail())
                .img1(productCreateRequestDTO.getImg1())
                .img2(productCreateRequestDTO.getImg2())
                .img3(productCreateRequestDTO.getImg3())
                .option(productCreateRequestDTO.getOption())
                .userId(user)
                .build();
    }
    // Entity to DTO 변환
    private SellerProductResponseDTO createSellerProductDTOFromEntity(ProductEntity productEntity) {
        return SellerProductResponseDTO.builder()
                .productId(productEntity.getProductId())
                .category(productEntity.getCategory().getCategory())
                .productName(productEntity.getProductName())
                .price(productEntity.getPrice())
                .productQuantity(productEntity.getProductQuantity())
                .registerDate(productEntity.getRegisterDate())
                .saleEndDate(productEntity.getSaleEndDate())
                .productDetail(productEntity.getProductDetail())
                .img1(productEntity.getImg1())
                .img2(productEntity.getImg2())
                .img3(productEntity.getImg3())
                .option(productEntity.getOption())
                .build();
    }
}
