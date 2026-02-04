package com.enesincekara.controller;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.PasswordChangeRequestDto;
import com.enesincekara.dto.request.UpdateRequestDto;
import com.enesincekara.dto.response.UserResponse;
import com.enesincekara.entity.SpecificDetailRequestDto;
import com.enesincekara.model.RegisterModel;
import com.enesincekara.model.UserContactResponse;
import com.enesincekara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PutMapping(CHANGE)
    public ResponseEntity<Boolean> changePassword(
            @RequestHeader(value = "Authorization",required = false) String bearerToken,
            @RequestBody PasswordChangeRequestDto req
    ){
        return ResponseEntity.ok(userService.changePassword(bearerToken,req.newPassword()));
    }

    @PutMapping(UPDATE_DETAILS)
    public ResponseEntity<Void> updateDetails(
            @RequestHeader(value = "Authorization",required = false)String bearerToken,
            @RequestBody SpecificDetailRequestDto req){
        userService.updateSpecificDetails(bearerToken,req);
        return ResponseEntity.ok().build();
    }

    @GetMapping(CONTACT)
    public ResponseEntity<UserContactResponse> getContactInfo(@PathVariable UUID authId) {
        return ResponseEntity.ok( userService.getContactInfo(authId));
    }

}
