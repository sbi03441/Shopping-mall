package com.b2.prj02.dto;

import com.b2.prj02.role.UserStatus;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
    private String status;
//    private UserStatus status;
}
