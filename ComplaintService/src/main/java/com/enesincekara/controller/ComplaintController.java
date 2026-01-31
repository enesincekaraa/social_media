package com.enesincekara.controller;


import com.enesincekara.dto.request.ComplaintRequestDto;
import com.enesincekara.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.enesincekara.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPLAINT)
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping("/create")
    public ResponseEntity<String> createComplaint(
            @RequestHeader(value = "Authorization" ,required = false) String bearerToken,
            @RequestBody ComplaintRequestDto dto) {

        String trackingNumber = complaintService.createComplaint(dto, bearerToken);

        return ResponseEntity.ok("Şikayetiniz alındı. Takip Numaranız: " + trackingNumber);
    }
}
