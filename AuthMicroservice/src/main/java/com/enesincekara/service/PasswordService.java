package com.enesincekara.service;

public interface PasswordService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
