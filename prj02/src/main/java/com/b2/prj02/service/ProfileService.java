package com.b2.prj02.service;

import com.b2.prj02.dto.reponse.ProfileInfoResponseDTO;
import com.b2.prj02.entity.User;
import com.b2.prj02.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileInfoResponseDTO findProfileInfoByProfileIdx(Long profileIdx) {
        User user = profileRepository.findById(profileIdx).orElseThrow(() -> new RuntimeException("없는 정보 입니다."));
        return ProfileInfoResponseDTO.from(user);
    }
}
