package com.b2.prj02.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Builder
@Setter
public class ProductListRequest {

    private String keyword;
    private int pageNumber;
    private int pageSize;
    private List<String> category;


}