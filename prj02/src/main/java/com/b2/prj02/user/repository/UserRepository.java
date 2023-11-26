package com.b2.prj02.user.repository;

import com.b2.prj02.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Query("SELECT u FROM User u WHERE u.userActiveStatus <> 'DELETED' AND u.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userActiveStatus <> 'DELETED' AND u.email =:email")
    UserDetails findUserDetailByEmail(@Param("email") String email);
}
