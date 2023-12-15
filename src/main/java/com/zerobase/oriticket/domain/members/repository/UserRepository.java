package com.zerobase.oriticket.domain.members.repository;

import com.zerobase.oriticket.domain.members.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

