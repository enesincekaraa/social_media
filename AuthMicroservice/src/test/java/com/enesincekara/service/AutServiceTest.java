package com.enesincekara.service;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.LoginRequestDto;
import com.enesincekara.dto.request.RegisterRequestDto;
import com.enesincekara.dto.response.LoginResponseDto;
import com.enesincekara.entity.Auth;
import com.enesincekara.rabbitmq.producer.AuthProducer;
import com.enesincekara.repository.AuthRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutServiceTest {

    @Mock
    private AuthRepository repository;

    @Mock
    private AuthProducer authProducer;

    @Mock
    private PasswordService passwordService;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @InjectMocks
    private AuthService authService;



    @Test
    @DisplayName("Success_Register_Case")
    void register_ShouldSaveUser_WhenUsernameAndEmailUnique() {
        RegisterRequestDto req = new RegisterRequestDto("enes", "123", "123", "enes@test.com");

        when(repository.isUsernameExists(anyString())).thenReturn(false);
        when(repository.isEmailExists(anyString())).thenReturn(false);

        when(passwordService.encode(anyString())).thenReturn("hashed_password");

        authService.register(req);

        verify(repository, times(1)).save(any(Auth.class));
        verify(authProducer, times(1)).sendCreateProfileMessage(any());
    }

    @Test
    @DisplayName("Error_Register_Case")
    void register_ShouldThrowException_WhenUsernameExists() {
        RegisterRequestDto req = new RegisterRequestDto
                ("enes","123","123","enes@test.com");

        when(repository.isUsernameExists("enes")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(req));

        verify(repository,never()).save(any());

    }

    @Test
    @DisplayName("Success_Login_Case")
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        UUID mockId = UUID.randomUUID();
        String username = "enes";
        String rawPassword = "123";
        String encodedPassword = "hashed_password";

        Auth mockAuth = createMockAuth(mockId, username, encodedPassword);

        when(repository.findActiveByUsername(username)).thenReturn(Optional.of(mockAuth));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtTokenManager.createToken(mockId)).thenReturn(Optional.of("fake_token"));

        LoginResponseDto response = authService.login(new LoginRequestDto(username, rawPassword));

        assertEquals("fake_token",response.token());
    }

    @Test
    @DisplayName("Error_Login_Case:User_Inactive")
    void login_ShouldThrowException_WhenUserIsInactive() {

        UUID mockId = UUID.randomUUID();
        String username = "inactive_user";

        Auth mockAuth = createMockAuth(mockId, username, "encoded_password");
        ReflectionTestUtils.setField(mockAuth,"isActive",false);

        when(repository.findActiveByUsername(username)).thenReturn(Optional.of(mockAuth));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.login(new LoginRequestDto(username, "any_password"));
        });

        assertEquals("Inactive user",exception.getMessage());
        verify(jwtTokenManager,never()).createToken(any());
    }

    @Test
    @DisplayName("Success_Soft_Delete_Case")
    void softDelete_ShouldCallRepository_WhenUserExists() {
        UUID mockId = UUID.randomUUID();
        when(repository.existsById(mockId)).thenReturn(true);
        authService.softDelete(mockId);

        verify(repository,times(1)).softDeleteById(mockId);
        verify(repository,never()).findActiveById(mockId);
    }

    @Test
    @DisplayName("Error_Soft_Delete:User not exists")
    void softDelete_ShouldThrowException_WhenUserNotExists() {
        UUID mockId = UUID.randomUUID();
        when(repository.existsById(mockId)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> authService.softDelete(mockId));
        verify(repository,never()).findActiveById(mockId);
    }









    private Auth createMockAuth(
            UUID id,
            String username,
            String encodedPassword
    ){
        RegisterRequestDto dummyDto = new RegisterRequestDto(
                username,
                "123",
                "123",
                "test@test.com"

        );
        Auth auth = Auth.create(dummyDto,passwordService);
        ReflectionTestUtils.setField(auth,"id",id);
        ReflectionTestUtils.setField(auth,"password",encodedPassword);
        return auth;

    }



}
