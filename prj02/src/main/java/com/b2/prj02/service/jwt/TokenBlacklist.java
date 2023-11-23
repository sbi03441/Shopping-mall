package com.b2.prj02.service.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist {
    @Getter
    private static Set<String> blacklist = new HashSet<>();

    public static void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

}