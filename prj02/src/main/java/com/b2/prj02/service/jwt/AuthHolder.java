package com.b2.prj02.service.jwt;

public class AuthHolder {
    private static final ThreadLocal<String> profileEmailHolder = new ThreadLocal<>();
    public static void setProfileEmail(String email){
        profileEmailHolder.set(email);
    }

    public static String getProfileEmail() {
        return profileEmailHolder.get();
    }

}
