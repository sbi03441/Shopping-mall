package com.b2.prj02.dto;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteRequestDTO {
    private String email;
    private String password;
}