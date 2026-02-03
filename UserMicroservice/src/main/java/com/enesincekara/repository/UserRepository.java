package com.enesincekara.repository;

import com.enesincekara.entity.User;
import com.enesincekara.projection.IUserProfileProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByAuthId(UUID authId);

    @Query(value = "{ 'authId' : ?0 }", fields = "{ 'username' : 1, 'email' : 1,'phone': 1, 'avatar' : 1, 'bio' : 1 }")
    Optional<IUserProfileProjection> findByAuthIdProjected(UUID authId);


    boolean existsByAuthId(UUID authId);
}
