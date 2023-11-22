package com.b2.prj02.controller;

import com.b2.prj02.dto.CategoryDTO;
import com.b2.prj02.repository.CategoryRepository;
import com.b2.prj02.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    //    카테고리 등록 * 제거 * 조회
    private final CategoryService categoryService;


    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO,
                                         @RequestHeader("X-AUTH-TOKEN") String token){
        return categoryService.addCategory(categoryDTO);
    }
}
