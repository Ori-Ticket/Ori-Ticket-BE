package com.zerobase.oriticket.user.repository;

import com.zerobase.oriticket.user.constants.SignupStatus;
import com.zerobase.oriticket.user.entity.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Page<Members> findByStatus(SignupStatus signupStatus, Pageable pageable);
}

