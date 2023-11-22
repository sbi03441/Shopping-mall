package com.b2.prj02.service.product;

import com.b2.prj02.dto.product.ProductDTO;

import java.awt.print.Pageable;

public interface ProductService {
    ProductDTO getProductById(Long productId);

    PaginationResponse<ProductListResponse> getProductList(ProductListRequest productListRequest, Pageable pageable);
}
