package com.b2.prj02.dto.request;

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
    private String status;
    private String filePath;
}
