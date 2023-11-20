package com.b2.prj02.repository;

import com.b2.prj02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(Long userId);
}
