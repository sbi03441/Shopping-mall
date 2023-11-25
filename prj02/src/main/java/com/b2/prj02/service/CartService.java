package com.b2.prj02.service;


import com.b2.prj02.dto.request.CartItemRequestDTO;
import com.b2.prj02.dto.response.ShoppingCartResponseDTO;
import com.b2.prj02.entity.ShoppingCart;
import com.b2.prj02.entity.User;
import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.repository.CartRepository;
import com.b2.prj02.repository.ProfileRepository;
import com.b2.prj02.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    public ResponseEntity<?> addCart(Long userId, CartItemRequestDTO cartItemRequest) {
        User user = getUser(userId);
        ShoppingCart data = dataAddCart(user, cartItemRequest);
        ShoppingCartResponseDTO response = ShoppingCartResponseDTO.from(data);

        return ResponseEntity.ok(response);
    }

    private ShoppingCart dataAddCart(User user, CartItemRequestDTO cartItemRequest) {
        Long productId = cartItemRequest.getProductId();
        Long amount = cartItemRequest.getAmount();
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new RuntimeException("존재 하지 않는 상품 입니다."));
        ShoppingCart shoppingCart = cartRepository.findByUserIdAndProductId(user,productEntity).orElse(null);

        if (shoppingCart == null){
            ShoppingCart newShoppingCart = ShoppingCart.builder()
                    .userId(user)
                    .productId(productEntity)
                    .amount(amount)
                    .build();
            cartRepository.save(newShoppingCart);
            return newShoppingCart;
        }

        shoppingCart.setAmount(amount);
        return shoppingCart;
    }


    // 아이디 기준으로 유저 가져오기
    private User getUser(Long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
    }
}