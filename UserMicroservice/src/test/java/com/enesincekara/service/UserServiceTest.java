package com.enesincekara.service;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.response.UserResponse;
import com.enesincekara.entity.User;
import com.enesincekara.rabbitmq.model.RegisterModel;
import com.enesincekara.rabbitmq.producer.UserProducer;
import com.enesincekara.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("Success_Profile_Case")
    void createNewProfile_ShouldSaveUser(){
        RegisterModel model = new RegisterModel(
                UUID.randomUUID(),
                "enes",
                "enes@gmal.com"
        );
        userService.createNewProfile(model);
        verify(userRepository,times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("Success_Last_Login_Case")
    void updateLastLogin_ShouldUpdateFields_WhenUserExists(){
        UUID authId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        String ip = "192.168.1.1";

        User mockUser=User.createProfile(authId,"enes",ip);
        when(userRepository.findByAuthId(authId)).thenReturn(Optional.of(mockUser));

        userService.updateLastLogin(authId,now,ip);
        verify(userRepository,times(1)).save(mockUser);


    }

    @Test
    @DisplayName("Success_Get_User_Profile_Case")
    void getProfile_ShouldReturnUserResponse_WhenTokenIsValid(){
        String bearerToken = "Bearer valid_token";
        String token = "valid_token";
        UUID authId = UUID.randomUUID();


        User mockUser=User.createProfile(authId,"enes","enes@test.com");

        when(jwtTokenManager.getIdFromToken(token)).thenReturn(Optional.of(authId));
        when(userRepository.findByAuthId(authId)).thenReturn(Optional.of(mockUser));

        UserResponse response = userService.getProfile(bearerToken);

        assertNotNull(response);
        assertEquals("enes",response.username());
        verify(userRepository,times(1)).findByAuthId(authId);


    }
}
