package com.b2.prj02.role;

import javax.persistence.Enumerated;

public enum UserStatus {
    SELLER("SELLER"), USER("USER"), DELETED("DELETED");

    private final String role;

    UserStatus(String role){
        this.role = role;
    }

}
