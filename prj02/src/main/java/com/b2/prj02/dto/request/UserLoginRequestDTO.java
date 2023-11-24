package com.b2.prj02.dto.request;

import com.b2.prj02.role.UserStatus;
import lombok.*;


@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
