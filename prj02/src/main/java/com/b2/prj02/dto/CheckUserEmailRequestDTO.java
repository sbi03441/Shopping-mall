package com.b2.prj02.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserEmailRequestDTO {
    private String email;
}