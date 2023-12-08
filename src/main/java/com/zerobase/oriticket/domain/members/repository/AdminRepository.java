package com.zerobase.oriticket.domain.members.repository;

import com.zerobase.oriticket.domain.members.constants.SignupStatus;
import com.zerobase.oriticket.domain.members.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Page<Admin> findByStatus(SignupStatus signupStatus, Pageable pageable);
}