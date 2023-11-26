package com.b2.prj02.service;

import com.b2.prj02.dto.response.ProfileInfoResponseDTO;

import com.b2.prj02.entity.User;
import com.b2.prj02.repository.ProfileRepository;
import com.b2.prj02.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;

//    public ProfileInfoResponseDTO findProfileInfoByProfileIdx(Long profileIdx) {
//        User user = profileRepository.findByUserId(profileIdx).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
//        return ProfileInfoResponseDTO.from(user);
//    }
    // 유저 정보 조회
    public ProfileInfoResponseDTO findProfileInfoByProfileEmail(Long id) {

    User user = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
    return ProfileInfoResponseDTO.from(user);
}

    // 장바구니 조회
//    public ShoppingCartListResponseDTO getShoppingCartList(String profileEmail) {
//        User user = getUser(profileEmail);
//        ShoppingCart response= cartRepository.findByUserId(user).orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
////        Long userId = user.getUserId();
////        List<ShoppingCart> data = cartRepository.findAllById(Collections.singleton(userId));
////        List<CartItemResponse> responses =
//        return ShoppingCartListResponseDTO.from(response);
//    }



    // 이메일 기준으로 유저 가져오기
    private User getUser(String email){
        return profileRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("없는 정보 입니다."));
    }

}

