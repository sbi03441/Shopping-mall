package com.b2.prj02.service;

import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.dto.request.ProductCreateRequestDTO;
import com.b2.prj02.dto.request.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.user.entity.User;

import com.b2.prj02.repository.CategoryRepository;

import com.b2.prj02.repository.product.ProductRepository;
import com.b2.prj02.user.repository.UserRepository;
import com.b2.prj02.config.security.jwt.JwtTokenProvider;
import com.b2.prj02.user.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    // 판매 물품 등록
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createProduct(ProductCreateRequestDTO productCreateRequestDTO, String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String userEmail = jwtTokenProvider.getUserEmail(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AccessDeniedException("사용자를 찾을 수 없습니다."));

        if (user.getUserRole() != UserRole.SELLER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("SELLER 권한이 없습니다.");
        }
        ProductEntity productEntity = CreateProductEntity(productCreateRequestDTO, user);

        ProductEntity savedProduct = productRepository.save(productEntity);

        // ProductEntity를 ProductDTO로 변환하여 반환
        return ResponseEntity.status(HttpStatus.OK).body(savedProduct.toDto());
    }

    @Transactional
    // 판매 물품 조회
    public List<ProductCreateRequestDTO> getActiveProducts(Long userId, String userEmail) {
        LocalDate currentDate = LocalDate.now();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> activeProducts = productRepository.findByUserIdAndSaleEndDateBefore(userId, currentDate);
        // 엔터티를 DTO로 변환
        List<ProductCreateRequestDTO> activeProductDTOs = new ArrayList<>();
        for (ProductEntity productEntity : activeProducts) {
            ProductCreateRequestDTO dto = createProductDTOFromEntity(productEntity);
            activeProductDTOs.add(dto);
        }

        return activeProductDTOs;

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

        productEntity.setImg1(sellerUpdateQuantityRequestDTO.getImg1());
        productEntity.setImg2(sellerUpdateQuantityRequestDTO.getImg2());
        productEntity.setImg3(sellerUpdateQuantityRequestDTO.getImg3());


        productEntity.setProductName(sellerUpdateQuantityRequestDTO.getProductName());
        productEntity.setPrice(sellerUpdateQuantityRequestDTO.getPrice());
        productEntity.setProductQuantity(updatedQuantity);
        productEntity.setOption(sellerUpdateQuantityRequestDTO.getOption());

        return productEntity.toDto();

    }

    // 판매 종료된 물품 조회
    public List<ProductCreateRequestDTO> getSoldProducts(Long userId, String userEmail) {
        LocalDate currentDate = LocalDate.now();

        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 이메일입니다."));

        List<ProductEntity> soldProducts = productRepository.findByUserIdAndSaleEndDateAfter(userId, currentDate);

        List<ProductCreateRequestDTO> soldProductDTOs = new ArrayList<>();
        for (ProductEntity productEntity : soldProducts) {
            ProductCreateRequestDTO dto = createProductDTOFromEntity(productEntity);
            soldProductDTOs.add(dto);
        }

        return soldProductDTOs;
    }

    LocalDate now = LocalDate.now();
    // ProductEntity 생성
    private ProductEntity CreateProductEntity(ProductCreateRequestDTO productCreateRequestDTO, User user) {
        return ProductEntity.builder()
                .category(categoryRepository.findByCategory(productCreateRequestDTO.getCategory()).get())
                .productName(productCreateRequestDTO.getProductName())
                .price(productCreateRequestDTO.getPrice())
                .productQuantity(productCreateRequestDTO.getProductQuantity())
                .registerDate(now)
                .saleEndDate(now)
                .productDetail(productCreateRequestDTO.getProductDetail())
                .img1(productCreateRequestDTO.getImg1())
                .img2(productCreateRequestDTO.getImg2())
                .img3(productCreateRequestDTO.getImg3())
                .option(productCreateRequestDTO.getOption())
                .userId(user)
                .build();
    }
    // Entity to DTO 변환
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
                .option(productEntity.getOption())
                .build();
    }

}
