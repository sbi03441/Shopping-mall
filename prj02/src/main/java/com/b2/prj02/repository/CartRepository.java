package com.b2.prj02.repository;

import com.b2.prj02.entity.ShoppingCart;
import com.b2.prj02.entity.User;
import com.b2.prj02.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart,Long> {
    Optional<ShoppingCart> findByUserId(User user);

    Optional<ShoppingCart> findByUserIdAndProductId(User user, ProductEntity productEntity);


}
