//package com.enesincekara.service;
//
//import com.enesincekara.dto.response.UserResponse;
//import com.enesincekara.entity.EsnafDetail;
//import com.enesincekara.entity.SpecificDetailRequestDto;
//import com.enesincekara.entity.User;
//import com.enesincekara.entity.enums.ERole;
//import com.enesincekara.exception.BaseException;
//import com.enesincekara.model.RegisterModel;
//import com.enesincekara.projection.IUserProfileProjection;
//import com.enesincekara.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private JwtTokenManager jwtTokenManager;
//
////    @Mock
////    private UserProducer userProducer;
////
////    @Mock
////    IUserProfileProjection userProfileProjection;
//
//    @InjectMocks
//    private UserService userService;
//
//
//    @Test
//    @DisplayName("Success_Profile_Case")
//    void createNewProfile_ShouldSaveUser(){
//        RegisterModel model = new RegisterModel(
//                UUID.randomUUID(),
//                "enes",
//                "enes@gmal.com",
//                ERole.VATANDAS
//        );
//        userService.createNewProfile(model);
//        verify(userRepository,times(1)).save(any(User.class));
//    }
//
//
//    @Test
//    @DisplayName("Success_Last_Login_Case")
//    void updateLastLogin_ShouldUpdateFields_WhenUserExists(){
//        UUID authId = UUID.randomUUID();
//        LocalDateTime now = LocalDateTime.now();
//        String ip = "192.168.1.1";
//
//
//        User mockUser=User.createProfile(authId,"enes",ip,ERole.VATANDAS);
//        when(userRepository.findByAuthId(authId)).thenReturn(Optional.of(mockUser));
//
//        userService.updateLastLogin(authId,now,ip);
//        verify(userRepository,times(1)).save(mockUser);
//
//
//    }
//
//    @Test
//    @DisplayName("Success_Get_User_Profile_Case")
//    void getProfile_ShouldReturnUserResponse_WhenTokenIsValid() {
//
//        String bearerToken = "Bearer valid_token";
//        String token = "valid_token";
//        UUID authId = UUID.randomUUID();
//
//        IUserProfileProjection mockProjection = mock(IUserProfileProjection.class);
//        when(mockProjection.getUsername()).thenReturn("enes");
//        when(mockProjection.getEmail()).thenReturn("enes@test.com");
//        when(mockProjection.getAvatar()).thenReturn("default_avatar");
//        when(mockProjection.getBio()).thenReturn("Hi, I am Enes");
//
//        when(jwtTokenManager.getIdFromToken(token)).thenReturn(Optional.of(authId));
//
//        when(userRepository.findByAuthIdProjected(authId)).thenReturn(Optional.of(mockProjection));
//
//        UserResponse response = userService.getProfile(bearerToken);
//
//        assertNotNull(response);
//        assertEquals("enes", response.username());
//        assertEquals("enes@test.com", response.email());
//
//        verify(userRepository, times(1)).findByAuthIdProjected(authId);
//    }
//
//    @Test
//    @DisplayName("Update Specific Details: Success Case for Esnaf")
//    void updateSpecificDetails_ShouldUpdate_WhenRoleMatches() {
//        String bearerToken = "Bearer valid_token";
//        UUID authId = UUID.randomUUID();
//
//        User mockUser = User.createProfile(authId, "esnaf_user", "test@test.com", ERole.ESNAF);
//        EsnafDetail esnafDetail = new EsnafDetail("Eynesil Bakkalı", "123456", true);
//        SpecificDetailRequestDto dto = new SpecificDetailRequestDto(esnafDetail, null, null, null);
//
//        when(jwtTokenManager.getIdFromToken(anyString())).thenReturn(Optional.of(authId));
//
//        when(userRepository.findByAuthId(authId)).thenReturn(Optional.of(mockUser));
//
//        // WHEN
//        userService.updateSpecificDetails(bearerToken, dto);
//
//        // THEN
//        verify(userRepository, times(1)).save(mockUser);
//        assertEquals("Eynesil Bakkalı", mockUser.getEsnafDetail().shopName());
//    }
//
//
//    @Test
//    @DisplayName("Update Specific Details: Error - Role Mismatch")
//    void updateSpecificDetails_ShouldUpdate_WhenRoleMismatch() {
//        String bearerToken = "Bearer valid_token";
//        UUID authId = UUID.randomUUID();
//        User mockUser = User.createProfile(authId, "vatandas_user", "test@test.com", ERole.VATANDAS);
//
//        EsnafDetail esnafDetail = new EsnafDetail("Hatalı İstek", "000", false);
//        SpecificDetailRequestDto dto = new SpecificDetailRequestDto(esnafDetail, null, null, null);
//        when(jwtTokenManager.getIdFromToken(anyString())).thenReturn(Optional.of(authId));
//        when(userRepository.findByAuthId(authId)).thenReturn(Optional.of(mockUser));
//
//        assertThrows(BaseException.class,()->{
//            userService.updateSpecificDetails(bearerToken, dto);
//        });
//        verify(userRepository, never()).save(any());
//    }
//
//}
