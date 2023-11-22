package com.b2.prj02.service;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.repository.CategoryRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
            Long category = productCreateRequestDTO.getCategory();

            // 이 부분에서 categoryEntity를 가져와야 합니다.
            CategoryEntity categoryEntity = categoryRepository.findById(category)
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

            ProductEntity productEntity = getCreateProductEntity(productCreateRequestDTO, user);
            productEntity.setCategory(categoryEntity);  // ProductEntity에 CategoryEntity 설정

            // 먼저 CategoryEntity 저장
            categoryRepository.save(categoryEntity);
            // 이후 ProductEntity 저장
            ProductEntity savedProduct = productRepository.save(productEntity);

            return ResponseEntity.status(HttpStatus.OK).body(savedProduct);
        } catch (Exception e) {
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.", e);
        }
    }

    private static ProductEntity getCreateProductEntity(ProductCreateRequestDTO productCreateRequestDTO, User user) {
        return ProductEntity.builder()
                .category(CategoryEntity.builder().category(productCreateRequestDTO.getCategory()).build())
                .productName(productCreateRequestDTO.getProductName())
                .price(productCreateRequestDTO.getPrice())
                .productQuantity(productCreateRequestDTO.getProductQuantity())
                .registerDate(productCreateRequestDTO.getRegisterDate())
                .saleEndDate(productCreateRequestDTO.getSaleEndDate())
                .productDetail(productCreateRequestDTO.getProductDetail())
                .img1(productCreateRequestDTO.getImg1())
                .img2(productCreateRequestDTO.getImg2())
                .img3(productCreateRequestDTO.getImg3())
                .option(productCreateRequestDTO.getOption() != null ? String.join(",", productCreateRequestDTO.getOption()) : null)
                .userId(user)
                .build();
    }

    private ProductCreateRequestDTO createProductDTOFromEntity(ProductEntity productEntity) {
        return ProductCreateRequestDTO.builder()
                .category(productEntity.getCategory() != null ? productEntity.getCategory().getCategory() : null)
                .productName(productEntity.getProductName())
                .price(productEntity.getPrice())
                .productQuantity(productEntity.getProductQuantity())
                .registerDate(productEntity.getRegisterDate())
                .saleEndDate(productEntity.getSaleEndDate())
                .productDetail(productEntity.getProductDetail())
                .img1(productEntity.getImg1())
                .img2(productEntity.getImg2())
                .img3(productEntity.getImg3())
                .option(productEntity.getOptionArray())
                .build();
    }

    // 판매 물품 조회
    public List<ProductCreateRequestDTO> getActiveProducts(Long userId, String userEmail) {
        LocalDateTime currentDate = LocalDateTime.now();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> activeProducts = productRepository.findByUserIdAndSaleEndDateBefore(user, currentDate);
        // 엔터티를 DTO로 변환
        List<ProductCreateRequestDTO> activeProductDTOs = new ArrayList<>();
        for (ProductEntity productEntity : activeProducts) {
            ProductCreateRequestDTO dto = createProductDTOFromEntity(productEntity);
            activeProductDTOs.add(dto);
        }

        return activeProductDTOs;

    }

    // 재고 수정
    public ProductEntity updateProductQuantity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO, String userEmail) {
        ProductEntity productEntity = productRepository.findById(sellerUpdateQuantityRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("물품을 찾을 수 없습니다."));

        if (!productEntity.getUserId().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("해당 물품의 판매자가 아닙니다.");
        }

        int updatedQuantity = sellerUpdateQuantityRequestDTO.getProductQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("재고는 음수가 될 수 없습니다.");
        }

        productEntity.setImg1(sellerUpdateQuantityRequestDTO.getImg1());
        productEntity.setImg2(sellerUpdateQuantityRequestDTO.getImg2());
        productEntity.setImg3(sellerUpdateQuantityRequestDTO.getImg3());

        CategoryEntity categoryEntity = categoryRepository.findById(sellerUpdateQuantityRequestDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        productEntity.setCategory(categoryEntity);

        productEntity.setProductName(sellerUpdateQuantityRequestDTO.getProductName());
        productEntity.setPrice(sellerUpdateQuantityRequestDTO.getPrice());
        productEntity.setProductQuantity(updatedQuantity);
        productEntity.setOption(Arrays.toString(sellerUpdateQuantityRequestDTO.getOption()));

        return productEntity;

    }

    // 판매 종료된 물품 조회
    public List<ProductCreateRequestDTO> getSoldProducts(Long userId, String userEmail) {
        LocalDateTime currentDate = LocalDateTime.now();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> soldProducts = productRepository.findByUserIdAndSaleEndDateAfter(user, currentDate);

        return soldProducts.stream()
                .map(this::createProductDTOFromEntity)
                .collect(Collectors.toList());
    }
}
