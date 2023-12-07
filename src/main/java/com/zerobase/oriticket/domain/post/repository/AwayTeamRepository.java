package com.zerobase.oriticket.domain.post.repository;

import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwayTeamRepository extends JpaRepository<AwayTeam, Long> {
}
