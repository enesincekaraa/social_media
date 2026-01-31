package com.enesincekara.service;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.entity.*;
import com.enesincekara.exception.BaseException;
import com.enesincekara.exception.ErrorType;
import com.enesincekara.model.RegisterModel;
import com.enesincekara.projection.IUserProfileProjection;
import com.enesincekara.rabbitmq.model.PasswordChangeModel;
import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.dto.request.UpdateRequestDto;
import com.enesincekara.dto.response.UserResponse;
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
                model.email(),
                model.eRole()
        );
        userRepository.save(user);
        System.out.println("Yeni Profil Oluşturuldu: " + user.getUsername() + " [Rol: " + user.getRole() + "]");
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

    public UserResponse getProfile(String bearerToken){ //public bir method UserResponse dönmeli. Once token içinden ıd yi alıyor
        UUID authId = getAuthId(getToken(bearerToken));

        IUserProfileProjection projection =userRepository.findByAuthIdProjected(authId)
                .orElseThrow(()-> new BaseException(ErrorType.USER_NOT_FOUND));

        return new UserResponse(
                projection.getUsername(),
                projection.getEmail(),
                projection.getAvatar(),
                projection.getBio()
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
                        ()-> new BaseException(ErrorType.USER_NOT_FOUND)
                );
        u.updateLastLogin(lastLoginDate, ip);
        userRepository.save(u);
        System.out.println("User last login date updated for: " + authId);

    }


    public Boolean changePassword(String bearerToken, String newPassword){
        UUID authId = getAuthId(getToken(bearerToken));
        if(!userRepository.existsByAuthId(authId)){
            throw new BaseException(ErrorType.USER_NOT_FOUND);
        }

        PasswordChangeModel model = new PasswordChangeModel(
                authId,
                newPassword
        );
        userProducer.sendPasswordChange(model);
        return true;
    }



    public void updateSpecificDetails(String bearerToken, SpecificDetailRequestDto dto){
        User user = getUser(getAuthId(bearerToken));
        switch (user.getRole()) {
            case ESNAF -> {
                if (dto.esnafDetail() != null) user.updateEsnafDetail(dto.esnafDetail());
            }
            case YASLI -> {
                if (dto.yasliDetail() != null) user.updateYasliDetail(dto.yasliDetail());
            }
            case SURUCU -> {
                if (dto.surucuDetail() != null) user.updateSurucuDetail(dto.surucuDetail());
            }
            case USTA -> {
                if (dto.ustaDetail() != null) user.updateUstaDetail(dto.ustaDetail());
            }
            default -> throw new BaseException(ErrorType.BAD_REQUEST_ERROR);
        }
        userRepository.save(user);
        System.out.println(user.getRole() + "successfully update user details " + user.getUsername());

    }

    private User getUser(UUID authId){
        return userRepository.findByAuthId(authId)
                .orElseThrow(()-> new BaseException(ErrorType.USER_NOT_FOUND));
    }
    private String getToken(String bearerToken){
        return bearerToken.substring(7);
    }
    private UUID getAuthId(String bearerToken){
        return jwtTokenManager.getIdFromToken(bearerToken)
                .orElseThrow(()-> new BaseException(ErrorType.USER_NOT_FOUND));
    }

    public void increaseUserComplaintCount(UUID authId) {
        User user = getUser(authId);
        user.incrementComplaintCount();
        userRepository.save(user);
        System.out.println("User complaint count updated for: " + user.getUsername());
    }
}
