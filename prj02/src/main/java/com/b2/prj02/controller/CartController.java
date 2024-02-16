package com.b2.prj02.controller;


import com.b2.prj02.dto.request.CartItemRequestDTO;
import com.b2.prj02.dto.request.ShoppingCartIdRequestDTO;
import com.b2.prj02.dto.response.ShoppingCartResponseDTO;
import com.b2.prj02.service.CartService;
import com.b2.prj02.service.jwt.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    //장바구니에 상품 담기(수정도 같이)
    @PostMapping()
    public ResponseEntity<ShoppingCartResponseDTO> addCart(@RequestBody CartItemRequestDTO cartItemRequest){
        Long userId = TokenContext.getProfileId();

        return cartService.addCart(userId,cartItemRequest);
    }


    //장바구니 조회
    @GetMapping
    public ResponseEntity<List<ShoppingCartResponseDTO>> getShoppingCart(){
        Long userId = TokenContext.getProfileId();

        return cartService.getShoppingCart(userId);
    }

    //장바구니 삭제
    @DeleteMapping("/select")
    public ResponseEntity<List<ShoppingCartResponseDTO>> deleteShoppingCart(@RequestBody ShoppingCartIdRequestDTO shoppingCartIdRequestDTO){
        Long userId = TokenContext.getProfileId();
        return cartService.deleteShoppingCart(userId,shoppingCartIdRequestDTO.getShoppingCartIdSet());
    }


}
