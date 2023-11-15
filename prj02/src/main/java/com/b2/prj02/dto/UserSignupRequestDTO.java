package com.b2.prj02.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
}
