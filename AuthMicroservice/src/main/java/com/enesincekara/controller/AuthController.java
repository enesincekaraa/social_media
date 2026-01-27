package com.enesincekara.controller;

import com.enesincekara.dto.request.LoginRequestDto;
import com.enesincekara.dto.request.RegisterRequestDto;
import com.enesincekara.dto.response.LoginResponseDto;
import com.enesincekara.entity.Auth;
import com.enesincekara.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.enesincekara.config.RestApis.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHSERVICE)
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(@RequestBody RegisterRequestDto req) {
        return ResponseEntity.ok(authService.register(req)) ;
    }
    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto req) {
        return ResponseEntity.ok(authService.login(req)) ;
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Void> internalUpdate(
            @RequestParam UUID id,
            @RequestParam String username,
            @RequestParam String email
            ){
        authService.updateAuth(id, username, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Void> softDelete(@RequestParam UUID id){
        authService.softDelete(id);
        return ResponseEntity.ok().build();
    }
}
