package com.b2.prj02.dto;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class UserSignupRequestDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
}
