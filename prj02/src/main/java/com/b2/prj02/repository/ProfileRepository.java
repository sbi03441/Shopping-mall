package com.b2.prj02.repository;

import com.b2.prj02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
//@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
public interface ProfileRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(Long userId);

    @Query("SELECT p FROM User p where p.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

}
