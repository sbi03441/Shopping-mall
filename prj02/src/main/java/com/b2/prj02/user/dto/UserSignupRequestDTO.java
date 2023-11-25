package com.b2.prj02.user.dto;


import com.b2.prj02.user.role.UserRole;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {
    private String email;
    private String nickName;
    private String password;
    private String address;
    private String gender;
    private UserRole userRole;
    private String filePath;
}
