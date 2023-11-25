package com.b2.prj02.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long category;
    private String categoryName;

    public CategoryDTO(Long category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }
}
