package com.b2.prj02.service;

import com.b2.prj02.dto.CategoryDTO;
import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public ResponseEntity<?> addCategory(CategoryDTO categoryDTO) {
        CategoryEntity newCategory = CategoryEntity.builder()
                .categoryName(categoryDTO.getCategoryName())
                .build();

        categoryRepository.save(newCategory);
        return ResponseEntity.status(200).body(categoryDTO.getCategoryName() + "가 카테고리로 추가되었습니다.");
    }
}
