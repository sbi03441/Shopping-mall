package com.b2.prj02.service;

import com.b2.prj02.dto.response.ProfileInfoResponseDTO;
import com.b2.prj02.dto.response.ShoppingCartListResponseDTO;
import com.b2.prj02.entity.User;
import com.b2.prj02.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;


//    public ProfileInfoResponseDTO findProfileInfoByProfileIdx(Long profileIdx) {
//        User user = profileRepository.findByUserId(profileIdx).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
//        return ProfileInfoResponseDTO.from(user);
//    }
    // 유저 정보 조회
    public ProfileInfoResponseDTO findProfileInfoByProfileEmail(String email) {

    User user = profileRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
    return ProfileInfoResponseDTO.from(user);
}

    // 장바구니 조회
    public ResponseEntity<List<ShoppingCartListResponseDTO>> getShoppingCartList(String profileEmail) {
        User user = getUser(profileEmail);
        //TODO 미완성
        return null;
    }



    // 이메일 기준으로 유저 가져오기
    private User getUser(String email){
        return profileRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("없는 정보 입니다."));
    }

}

