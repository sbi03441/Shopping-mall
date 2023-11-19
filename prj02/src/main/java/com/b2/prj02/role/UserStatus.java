package com.b2.prj02.role;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Enumerated;
@CrossOrigin(origins = "*")
public enum UserStatus {
    SELLER, USER, DELETED;

}
