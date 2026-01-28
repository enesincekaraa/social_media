package com.enesincekara.service;

import com.enesincekara.rabbitmq.model.RegisterModel;
import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.UpdateRequestDto;
import com.enesincekara.dto.response.UserResponse;
import com.enesincekara.entity.User;
import com.enesincekara.rabbitmq.model.UpdateUserModel;
import com.enesincekara.rabbitmq.producer.UserProducer;
import com.enesincekara.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final UserProducer userProducer;

    public void createNewProfile(RegisterModel model){
        User user = User.createProfile(
                model.authId(),
                model.username(),
                model.email()
        );
        userRepository.save(user);
        System.out.println("New Profile created: " + user.getUsername());
    }


    public boolean update(UpdateRequestDto req, String bearerToken){

        UUID authId = getAuthId(getToken(bearerToken));
        User u =  getUser(authId);
        u.update(req.username(), req.email());
        userRepository.save(u);

        UpdateUserModel model=new UpdateUserModel(
                authId,
                req.username(),
                req.email()
        );
        userProducer.sendUpdatedUser(model);
        return true;
    }

    public UserResponse getProfile(String bearerToken){
        UUID authId = getAuthId(getToken(bearerToken));
        User u =  getUser(authId);
        return new UserResponse(
                u.getUsername(),
                u.getEmail(),
                u.getAvatar(),
                u.getBio()
        );
    }

    public Boolean deleteMyAccount(String bearerToken){
        UUID authId = getAuthId(getToken(bearerToken));
        User u =  getUser(authId);
        userRepository.delete(u);

        SoftDeleteModel model =  new SoftDeleteModel(
          authId
        );
        userProducer.sendSoftDelete(model);
        return true;
    }

    public void updateLastLogin(UUID authId, LocalDateTime lastLoginDate, String ip){
        User u = userRepository.findByAuthId(authId)
                .orElseThrow(
                        ()-> new RuntimeException("User not found!")
                );
        u.updateLastLogin(lastLoginDate, ip);
        userRepository.save(u);
        System.out.println("User last login date updated for: " + authId);

    }


    private User getUser(UUID authId){
        return userRepository.findByAuthId(authId)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    private String getToken(String bearerToken){
        return bearerToken.substring(7);
    }
    private UUID getAuthId(String bearerToken){
        return jwtTokenManager.getIdFromToken(bearerToken)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
    }
}
