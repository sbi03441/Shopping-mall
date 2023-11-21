package com.b2.prj02.controller;

import com.b2.prj02.dto.reponse.ProfileInfoResponseDTO;
import com.b2.prj02.entity.User;
import com.b2.prj02.service.ProfileService;
import com.b2.prj02.service.jwt.AuthHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping("/info")
    public ResponseEntity<?> getUserProfile() {
        String profileEmail = AuthHolder.getProfileEmail();
        ProfileInfoResponseDTO profileInfoResponse = profileService.findProfileInfoByProfileEmail(profileEmail);
        return ResponseEntity.ok(profileInfoResponse);
    }
}
