package com.enesincekara.service;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.LoginRequestDto;
import com.enesincekara.dto.request.RegisterRequestDto;
import com.enesincekara.dto.response.LoginResponseDto;
import com.enesincekara.entity.Auth;
import com.enesincekara.rabbitmq.model.RegisterModel;
import com.enesincekara.rabbitmq.producer.AuthProducer;
import com.enesincekara.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthRepository repository;
    private final PasswordService passwordService;
    private final JwtTokenManager jwtTokenManager;
    private final AuthProducer authProducer;

    public Auth register(RegisterRequestDto req) {
        if (repository.existsByUsername(req.username())) {
            throw new RuntimeException("Username is already in use");
        }
        if (repository.existsByEmail(req.email())) {
            throw new RuntimeException("Email is already in use");
        }
        Auth auth = Auth.create(req,passwordService);

        RegisterModel model = new RegisterModel(
                auth.getId(),
                auth.getUsername(),
                auth.getEmail()
        );

        authProducer.sendCreateProfileMessage(model);
       return repository.save(auth);
    }

    public LoginResponseDto login(LoginRequestDto req) {
        Auth a = getActiveUser(req.username());
        if (!a.validateLogin(req.password(), passwordService)){
           throw new RuntimeException("Wrong password");
        }
       String token = jwtTokenManager.createToken(a.getId()).orElseThrow(
               ()-> new RuntimeException("Token cannot generate ")
       );
       return new LoginResponseDto(token);
    }

    public void updateAuth(UUID id, String username, String email) {
       Auth a = getActiveUserById(id);
       a.update(username, email);
       repository.save(a);
    }

    public void softDelete(UUID id) {
        Auth a = repository.findById(id).orElseThrow(
                ()-> new RuntimeException("No such user")
        );
        a.deactivate();
        repository.save(a);
    }






    private Auth getActiveUser(String username){
        return repository.findByUsernameAndIsActiveTrue(username).orElseThrow(
                ()-> new RuntimeException("Username not found")
        );
    }

    private Auth getActiveUserById(UUID id){
        return repository.findByIdAndIsActiveTrue(id).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
    }
}
