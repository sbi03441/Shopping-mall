package com.b2.prj02.dto.request;

import com.b2.prj02.entity.User;
import com.b2.prj02.role.UserStatus;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {
    private String email;
    private String nickName;
    private String password;
//    private String phoneNumber;
    private String address;
    private String gender;
    private String status;
}
