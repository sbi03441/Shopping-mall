package com.b2.prj02.mapper;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "category", source = "category")
    ProductEntity toSellerProductEntity(ProductCreateRequestDTO productCreateRequestDTO);


    ProductEntity toProductEntity(ProductCreateRequestDTO productCreateRequestDTO);

    ProductCreateRequestDTO toSellerProductDTO(ProductEntity productEntity);

    // 추가: 배열을 문자열로 매핑
    default String map(String[] value) {
        return value != null ? String.join(",", value) : null;
    }
    // 추가: 문자열을 배열로 매핑
    default String[] map(String value) {
        return value != null ? value.split(",") : null;
    }

    // 추가: Long을 CategoryEntity로 매핑
    default CategoryEntity map(Long category) {
        return new CategoryEntity(category);
    }

    ProductEntity updateEntity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO, ProductEntity productEntity);
}
