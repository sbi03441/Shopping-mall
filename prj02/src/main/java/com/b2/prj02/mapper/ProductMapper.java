package com.b2.prj02.mapper;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

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


}
