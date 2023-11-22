package com.b2.prj02.dto.request;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
