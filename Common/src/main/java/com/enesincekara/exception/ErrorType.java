package com.enesincekara.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // Auth & User Ortak Hatalar
    USER_NOT_FOUND(4004, "Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR(9999, "Beklenmedik bir sunucu hatası oluştu!", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(4001, "Geçersiz parametre girişi!", HttpStatus.BAD_REQUEST),

    // Auth Servis Hataları
    INVALID_TOKEN(5001, "Geçersiz token!", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(5002, "Şifre hatalı!", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(5003, "Bu kullanıcı adı zaten alınmış!", HttpStatus.BAD_REQUEST);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
