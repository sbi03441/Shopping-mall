package com.b2.prj02.repository;

import com.b2.prj02.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
