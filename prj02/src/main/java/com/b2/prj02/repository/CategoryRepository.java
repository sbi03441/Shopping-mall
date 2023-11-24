package com.b2.prj02.repository;

import com.b2.prj02.entity.CategoryEntity;
import com.b2.prj02.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
