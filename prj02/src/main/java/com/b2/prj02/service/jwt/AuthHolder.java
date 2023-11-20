package com.b2.prj02.service.jwt;

public class AuthHolder {
    private static final ThreadLocal<Long> profileIdxHolder = new ThreadLocal<>();
    public static void setProfileIdx(Long userId){
        profileIdxHolder.set(userId);
    }

    public static Long getProfileIdx() {
        return profileIdxHolder.get();
    }
}
