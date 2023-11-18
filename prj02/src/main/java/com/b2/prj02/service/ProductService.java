package com.b2.prj02.service;

import com.b2.prj02.dto.CategoryDTO;
import com.b2.prj02.dto.ProductDTO;
import com.b2.prj02.dto.SellerProductResponseDTO;
import com.b2.prj02.entity.ProductEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.mapper.ProductMapper;
import com.b2.prj02.repository.ProductRepository;
import com.b2.prj02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 쇼핑몰 판매 물품 등록
    public void createProduct(ProductDTO productDTO) {
        // 사용자 이메일을 기반으로 사용자 정보를 가져옴
        String userEmail = getUserEmailFromSecurityContext();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));



        // ProductDTO를 ProductEntity로 변환
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productDTO);

        // 판매 등록 시 유저 정보 연결
        productEntity.setUserId(user);

        // 물품 등록 날짜 설정
        productEntity.setRegisterDate(LocalDateTime.now());

        // 설정 완료 물품 db에 등록
        ProductEntity savedProduct = productRepository.save(productEntity);

        // 등록된 물품 정보 다시 DTO로 변환하여 반환
        ProductMapper.INSTANCE.toDTO(savedProduct);
    }

    // 판매 물품 조회
    public List<SellerProductResponseDTO> getActiveProducts() {
        // 현재 날짜를 가져오는 로직을 추가.
        LocalDateTime currentDate = LocalDateTime.now();

        List<ProductEntity> activeProducts = productRepository.findBySaleEndDateAfter(currentDate);

        /*// Entity를 DTO로 변환
        return ProductMapper.INSTANCE.toDTOList(activeProducts);*/

        return activeProducts.stream()
                .map(product -> new SellerProductResponseDTO(
                        product.getId(),
                        product.getImg1(), // 이미지 경로 등의 정보가 있는 것으로 가정
                        product.getCategory() != null ? new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName()) : null,
                        product.getProductName(),
                        product.getPrice(),
                        product.getProductQuantity(),
                        product.getOption() != null ? product.getOption().split(",") : new String[0],
                        0))
                .collect(Collectors.toList());
    }

    public void updateProductQuantity(SellerProductResponseDTO sellerProductResponseDTO) {
        ProductEntity productEntity = productRepository.findById(sellerProductResponseDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("물품을 찾을 수 없습니다."));

        // 재고 수정 로직 추가
        int updatedQuantity = sellerProductResponseDTO.getUpdatedProductQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("재고는 음수가 될 수 없습니다.");
        }

        productEntity.setProductQuantity(updatedQuantity);
        productRepository.save(productEntity);
    }


        private String getUserEmailFromSecurityContext() {
        // SecurityContext에서 사용자 이메일을 가져오는 로직을 구현
        SecurityContextHolder.getContext().getAuthentication().getName();
        return "user@example.com";
    }



}
