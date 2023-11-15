package com.b2.prj02.service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist {
    private static Set<String> blacklist = new HashSet<>();

    public static void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}