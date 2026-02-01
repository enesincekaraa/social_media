package com.enesincekara.repository;

import com.enesincekara.entity.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {


    @Query(value = "{'authId' :  ?0 }" ,sort = "{'createdAt': -1}")
    List<Complaint> findUserComplaint(UUID authId);


    @Query(value = "{}",sort = "{'createdAt': -1}")
    List<Complaint> findAllComplaintsOrdered();

    @Query(value = "{'trackingNumber': ?0}")
    Optional<Complaint> findByTrackingCode(String trackingNumber);
}
