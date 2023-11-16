package com.b2.prj02.service;

import com.b2.prj02.dto.ProductDTO;
import com.b2.prj02.entity.ProductEntity;
import com.b2.prj02.entity.User;
import com.b2.prj02.mapper.ProductMapper;
import com.b2.prj02.repository.ProductRepository;
import com.b2.prj02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        // 판매자 권한 체크
        if (!user.isSeller()) {
            throw new AccessDeniedException("판매자 권한이 없습니다.");
        }

        // 물품 정보 유효성 검사.
       /* if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }*/

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

    private String getUserEmailFromSecurityContext() {
        // SecurityContext에서 사용자 이메일을 가져오는 로직을 구현
        SecurityContextHolder.getContext().getAuthentication().getName();
        return "user@example.com";
    }



}
