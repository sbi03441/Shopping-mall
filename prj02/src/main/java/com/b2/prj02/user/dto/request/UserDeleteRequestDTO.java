package com.b2.prj02.user.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteRequestDTO {
    private String email;
    private String password;
}