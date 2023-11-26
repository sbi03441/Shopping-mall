package com.b2.prj02.dto.response;


import com.b2.prj02.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId; // 유저 id

    public static UserResponseDTO from(User user){
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .build();
    }
}
