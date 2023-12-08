package com.zerobase.oriticket.domain.members.repository;

import com.zerobase.oriticket.domain.members.constants.SignupStatus;
import com.zerobase.oriticket.domain.members.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Member, Long> {
    Page<Member> findByStatus(SignupStatus signupStatus, Pageable pageable);
}

