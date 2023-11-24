package com.b2.prj02.controller;

import com.b2.prj02.dto.response.ProfileInfoResponseDTO;
import com.b2.prj02.dto.response.ShoppingCartListResponseDTO;
import com.b2.prj02.service.ProfileService;
import com.b2.prj02.service.jwt.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
public class ProfileController {

    private final ProfileService profileService;
    // 유저 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getUserProfile() {
        String profileEmail = AuthHolder.getProfileEmail();
        ProfileInfoResponseDTO profileInfoResponse = profileService.findProfileInfoByProfileEmail(profileEmail);
        return ResponseEntity.ok(profileInfoResponse);
    }
    // 장바구니 조회
    // TODO : 미완성
    @GetMapping("/cart")
    public ResponseEntity<List<ShoppingCartListResponseDTO>> getShoppingCart(){
        String profileEmail = AuthHolder.getProfileEmail();
        return profileService.getShoppingCartList(profileEmail);
    }

    // 구매 내역 조회



}
