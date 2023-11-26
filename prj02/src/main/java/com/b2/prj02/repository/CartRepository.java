package com.b2.prj02.repository;

import com.b2.prj02.entity.ShoppingCart;
import com.b2.prj02.entity.User;
import com.b2.prj02.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart,Long> {




    List<ShoppingCart> findAllByUserIdAndIsDeletedIsFalseOrderByCreatedAtDesc(User user);

    Optional<ShoppingCart> findByUserIdAndProductIdAndIsDeletedIsFalse(User user, ProductEntity productEntity);

    List<ShoppingCart> findAllByUserIdAndIsDeletedIsFalseAndCartIdIsIn(User user, Collection<Long>shoppingCartId);
}
