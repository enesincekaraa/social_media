package com.enesincekara.service;

import com.enesincekara.config.JwtTokenManager;
import com.enesincekara.dto.request.ComplaintRequestDto;
import com.enesincekara.dto.response.ComplaintResponse;
import com.enesincekara.entity.Complaint;
import com.enesincekara.entity.enums.ERole;
import com.enesincekara.entity.enums.EStatus;
import com.enesincekara.exception.BaseException;
import com.enesincekara.exception.ErrorType;
import com.enesincekara.model.ComplaintCreateModel;
import com.enesincekara.rabbitmq.producer.ComplaintProducer;
import com.enesincekara.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {

    private final ComplaintRepository repository;
    private final ComplaintProducer producer;
    private final JwtTokenManager jwtTokenManager;

    public String createComplaint(ComplaintRequestDto req, String bearerToken) {
        UUID authId = getAuthId(bearerToken);

        Complaint c =Complaint.create(
                authId,
                req.title(),
                req.description(),
                req.category(),
                req.imageUrls(),
                req.location()
        );
        repository.save(c);
        System.out.println("Complaint created successfully");
        producer.sendCreateComplaintMessage(new ComplaintCreateModel(
                c.getId(),authId, c.getTitle(), c.getCategory(),c.getCreatedAt()
        ));
        System.out.println("Complaint sending  successfully");


        return c.getTrackingNumber();

    }


    public List<ComplaintResponse> getMyComplaints(String bearerToken) {
        UUID authId = getAuthId(bearerToken);
        return repository.findUserComplaint(authId)
                .stream().map(this::mapToDto)
                .toList();
    }

    public List<ComplaintResponse> getAllForAdmin(String bearerToken) {
        checkAdminRole(bearerToken);
        return repository.findAllComplaintsOrdered()
                .stream().map(this::mapToDto)
                .toList();

    }

    @Transactional
    public void updateComplaintStatus(String id, EStatus newStatus,String bearerToken) {
       checkAdminRole(bearerToken);
        Complaint complaint = repository.findById(id)
                .orElseThrow(
                        ()-> new BaseException(ErrorType.COMPLAINT_NOT_FOUND)
                );
        complaint.updateStatus(newStatus);
        repository.save(complaint);
    }




    private ComplaintResponse mapToDto(Complaint complaint) {
        return new ComplaintResponse(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getTrackingNumber(),
                complaint.getCreatedAt(),
                complaint.getImageUrls()
        );
    }



    private UUID getAuthId(String bearerToken) {
        if (bearerToken == null|| !bearerToken.startsWith("Bearer ")) {
            throw new BaseException(ErrorType.INVALID_TOKEN);
        }
        String token = bearerToken.substring(7);
        return jwtTokenManager.getIdFromToken(token)
                .orElseThrow(()->new BaseException(ErrorType.USER_NOT_FOUND));
    }

    private void checkAdminRole(String bearerToken) {
        if (bearerToken == null|| !bearerToken.startsWith("Bearer ")) {
            throw new BaseException(ErrorType.INVALID_TOKEN);
        }
        String token = bearerToken.substring(7);
        String role = jwtTokenManager.getRoleFromToken(token)
                .orElseThrow(() -> new BaseException(ErrorType.INVALID_TOKEN));
        if (!role.equals(ERole.ADMIN.name())) {
            throw new BaseException(ErrorType.BAD_REQUEST_ERROR);
        }
    }


}
