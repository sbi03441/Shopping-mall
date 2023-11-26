package com.b2.prj02.user.dto.response;


import com.b2.prj02.user.role.UserRole;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    private String email;
    private String nickName;
    private String address;
    private String gender;
    private UserRole userRole;
    private String filePath;
    private String token;
}
