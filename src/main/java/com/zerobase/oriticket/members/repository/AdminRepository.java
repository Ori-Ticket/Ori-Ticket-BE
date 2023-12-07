package com.zerobase.oriticket.members.repository;

import com.zerobase.global.constants.SignupStatus;
import com.zerobase.oriticket.members.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Page<Admin> findByStatus(SignupStatus signupStatus, Pageable pageable);
}