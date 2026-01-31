package com.enesincekara.repository;

import com.enesincekara.entity.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {
}
