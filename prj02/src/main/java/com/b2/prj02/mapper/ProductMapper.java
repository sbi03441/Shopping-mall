package com.b2.prj02.mapper;

import com.b2.prj02.dto.ProductDTO;
import com.b2.prj02.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    // 엔티티와 DTO 간의 매핑을 간편하게 자동화할 수 있다.
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(ProductEntity productEntity);

    ProductEntity toEntity(ProductDTO productDTO);
}
