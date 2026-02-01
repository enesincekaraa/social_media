package com.enesincekara.controller;


import com.enesincekara.dto.request.ComplaintRequestDto;
import com.enesincekara.dto.response.ComplaintResponse;
import com.enesincekara.entity.enums.EStatus;
import com.enesincekara.service.ComplaintService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.enesincekara.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPLAINT)
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping(COMPLAINT_CREATE)
    public ResponseEntity<String> createComplaint(
            @RequestHeader(value = "Authorization" ,required = false) String bearerToken,
            @RequestBody ComplaintRequestDto dto) {

        String trackingNumber = complaintService.createComplaint(dto, bearerToken);

        return ResponseEntity.ok("Şikayetiniz alındı. Takip Numaranız: " + trackingNumber);
    }
    @GetMapping("my-complaints")
    public ResponseEntity<List<ComplaintResponse>>  getMyComplaints(
            @RequestHeader(value = "Authorization" ,required = false) String bearerToken)
    {
        return ResponseEntity.ok(complaintService.getMyComplaints(bearerToken));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ComplaintResponse>> getAll(@RequestHeader(value = "Authorization" ,required = false) String bearerToken) {
        return ResponseEntity.ok(complaintService.getAllForAdmin(bearerToken));
    }

    @PatchMapping("/admin/status/{id}")
    public ResponseEntity<Void> updateStatus(
            @RequestHeader(value = "Authorization" ,required = false) String bearerToken,
            @PathVariable String id,
            @RequestParam EStatus status
            )
    {
        complaintService.updateComplaintStatus(id, status, bearerToken);
        return ResponseEntity.ok().build();
    }
}
