//package com.b2.prj02.service.product;
//
//import com.b2.prj02.dto.product.ProductDTO;
//import com.b2.prj02.entity.product.ProductEntity;
//import com.b2.prj02.exception.product.ProductNotFoundException;
//import com.b2.prj02.repository.product.ProductRepository;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityNotFoundException;
//import javax.transaction.Transactional;
//import java.rmi.NotBoundException;
//import java.util.Optional;
//
//
//@Slf4j
//@Service
//@Builder
//@RequiredArgsConstructor
////Service에서 DTO to Entity, Entity to DTO.
//public class ProductServiceImpl implements ProductService {
//
//    private final ProductRepository productRepository;
//
//
//    @Transactional
//    @Override
//    // 상품 조회
//    public ProductDTO getProductById(Long productId) {
//        try {
//            Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
//            if (optionalProduct.isPresent()) {
//                ProductEntity findProduct = optionalProduct.get();
//                ProductDTO productDTO = ProductDTO.toProductDTO(findProduct);
//                return productDTO;
//            } else {
//                throw new NullPointerException();
//            }
//        } catch (Exception e) {
//            throw new ProductNotFoundException("상품을 찾을 수 없습니다.");
//        }
//    }
//
//}
//
