package com.b2.prj02.controller;


import com.b2.prj02.dto.request.CartItemRequestDTO;
import com.b2.prj02.service.CartService;
import com.b2.prj02.service.jwt.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    //장바구니에 상품 담기
    @PostMapping()
    public ResponseEntity<?> addCart(@RequestBody CartItemRequestDTO cartItemRequest){
        Long userId = TokenContext.getProfileId();

        return cartService.addCart(userId,cartItemRequest);
    }
}
