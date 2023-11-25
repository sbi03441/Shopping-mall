package com.b2.prj02.user.role;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

    private final String role;

    public CustomGrantedAuthority(UserRole userRole) {
        // 여기에서 사용자 상태에 따라 권한을 할당
        this.role = "ROLE_" + userRole.name();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}