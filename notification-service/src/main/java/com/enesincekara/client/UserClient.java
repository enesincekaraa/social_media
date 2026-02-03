package com.enesincekara.client;


import com.enesincekara.model.UserContactResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service" ,url = "http://localhost:9091/dev/v1/user")
public interface UserClient {

    @GetMapping("/contact-info/{authId}")
    UserContactResponse getContactInfo(@PathVariable("authId") UUID authId);
}
