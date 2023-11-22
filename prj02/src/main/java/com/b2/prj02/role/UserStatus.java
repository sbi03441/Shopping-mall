package com.b2.prj02.role;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Enumerated;
@CrossOrigin(origins = "*")
public enum UserStatus {
    SELLER("SELLER"), USER("USER"), DELETED("DELETED");

    private final String role;

    UserStatus(String role){
        this.role = role;
    }

}
