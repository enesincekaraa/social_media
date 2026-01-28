package com.enesincekara.repository;

import com.enesincekara.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, Long> {


    boolean existsById(UUID id);

    @Query("""
            Select Count(a) > 0 From Auth a
            Where a.username = :username
            """)
    boolean isUsernameExists(@Param("username") String username);


    @Query("""
            Select Count(a) > 0 From Auth a
            Where a.email = :email
            """)
    boolean isEmailExists(@Param("email") String email);

    @Query("""
            Select a from Auth a 
            Where a.username = :username 
            And a.isActive = true
            """)
    Optional<Auth> findActiveByUsername(@Param("username") String username);

    @Query("""
            Select a from Auth a
            Where a.Id = :Id
            And a.isActive = true
            """)
    Optional<Auth> findActiveById(@Param("Id") UUID id);


    @Query("""
            Select count(a) > 0 from Auth a
            Where a.username = :username
            
            """)
    boolean isUsernameTaken(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("""
            Update Auth a 
            Set a.isActive = false 
            Where a.id =:id
            """)
    void softDeleteById(@Param("id") UUID id);


}
