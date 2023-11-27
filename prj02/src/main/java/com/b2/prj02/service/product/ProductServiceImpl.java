package com.b2.prj02.service.product;

import com.b2.prj02.Exception.product.ProductNotFoundException;
import com.b2.prj02.dto.product.ProductDTO;
import com.b2.prj02.dto.request.ProductListRequest;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.repository.product.ProductRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Builder
@RequiredArgsConstructor
//Service에서 DTO to Entity, Entity to DTO.
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Transactional
    @Override
    // 상품 조회
    public ProductDTO getProductById(Long productId) {
        try {
            Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                ProductEntity findProduct = optionalProduct.get();
                ProductDTO productDTO = ProductDTO.toProductDTO(findProduct);
                return productDTO;
            } else {
                throw new ProductNotFoundException("상품을 찾을 수 없습니다.");
            }
        }   catch (Exception e) {
            throw new ProductNotFoundException("상품 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    public PaginationResponse<ProductListResponse> getProductList(ProductListRequest productListRequest, Pageable pageable) {
        // 페이지네이션 처리 로직 추가
        Page<ProductEntity> productPage = productRepository.findAll(pageable);


        // productEntity를 productDTO로 변환하여 리스트로 담기
        List<ProductDTO> productDTOList = productPage.getContent().stream()
                .map(ProductDTO::toProductDTO)
                .collect(Collectors.toList());

        return new PaginationResponse<>(productDTOList, productPage.getTotalElements(), productPage.getTotalPages());
    }
}


