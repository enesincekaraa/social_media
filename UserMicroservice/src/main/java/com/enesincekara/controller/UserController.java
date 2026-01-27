package com.enesincekara.controller;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.UpdateRequestDto;
import com.enesincekara.dto.request.UpdateUserProfileRequestDto;
import com.enesincekara.dto.request.UserCreateRequestDto;
import com.enesincekara.dto.response.UserResponse;
import com.enesincekara.entity.User;
import com.enesincekara.rabbitmq.model.RegisterModel;
import com.enesincekara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enesincekara.config.RestApis.*;

@RestController
@RequestMapping(USERSERVICE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping(CREATE)
    public ResponseEntity<Void> createNewProfile(
            @RequestBody RegisterModel model)
    {
        userService.createNewProfile(model);
        return ResponseEntity.ok().build();
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateProfile(
            @RequestBody UpdateRequestDto req,
            @RequestHeader(value = "Authorization", required = false) String bearerToken)
    {
        return ResponseEntity.ok(userService.update(req, bearerToken));
    }

    @GetMapping(GETPROFILE)
    public ResponseEntity<UserResponse> getProfile(
            @RequestHeader(value = "Authorization", required = false) String bearerToken)
    {
        return ResponseEntity.status(200).body(userService.getProfile(bearerToken));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteProfile(@RequestHeader(value = "Authorization",required = false)String bearerToken){
        return ResponseEntity.ok(userService.deleteMyAccount(bearerToken));
    }

}
