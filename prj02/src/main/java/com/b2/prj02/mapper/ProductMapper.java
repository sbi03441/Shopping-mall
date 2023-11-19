package com.b2.prj02.mapper;

import com.b2.prj02.dto.sellerDTO.ProductCreateRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerProductRequestDTO;
import com.b2.prj02.dto.sellerDTO.SellerUpdateQuantityRequestDTO;
import com.b2.prj02.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductCreateRequestDTO toCreateDTO(ProductEntity productEntity);

    ProductEntity toEntity(ProductCreateRequestDTO productCreateRequestDTO);

    SellerProductRequestDTO toSellerProductDTO(ProductEntity productEntity);


    // 추가: 배열을 문자열로 매핑
    default String map(String[] value) {
        return value != null ? String.join(",", value) : null;
    }
    // 추가: 문자열을 배열로 매핑
    default String[] map(String value) {
        return value != null ? value.split(",") : null;
    }

    @Mapping(target = "productId",source = "sellerUpdateQuantityRequestDTO.productId")
    ProductEntity updateEntity(SellerUpdateQuantityRequestDTO sellerUpdateQuantityRequestDTO,@MappingTarget ProductEntity productEntity);

}
