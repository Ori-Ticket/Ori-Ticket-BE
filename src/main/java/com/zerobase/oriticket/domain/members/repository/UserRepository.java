package com.zerobase.oriticket.domain.members.repository;

import com.zerobase.oriticket.domain.members.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    UserEntity findById(long id);
}