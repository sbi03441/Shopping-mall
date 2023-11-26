package com.b2.prj02.controller;


import com.b2.prj02.dto.response.ProfileInfoResponseDTO;


import com.b2.prj02.dto.response.ShoppingCartResponseDTO;
import com.b2.prj02.service.CartService;
import com.b2.prj02.service.ProfileService;
import com.b2.prj02.service.jwt.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
//@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
public class ProfileController {

    private final ProfileService profileService;
    private final CartService cartService;
    // 유저 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getUserProfile() {
        Long profileId = TokenContext.getProfileId();
        ProfileInfoResponseDTO profileInfoResponse = profileService.findProfileInfoByProfileEmail(profileId);
        return ResponseEntity.ok(profileInfoResponse);
    }

    // 유저 장바구니 조회
    @GetMapping("/cart")
    public ResponseEntity<List<ShoppingCartResponseDTO>> getShoppingCart(){
        Long userId = TokenContext.getProfileId();

        return cartService.getShoppingCart(userId);
    }

    // 구매 내역 조회



}
