package com.zerobase.oriticket.post.repository;

import com.zerobase.oriticket.post.entity.AwayTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwayTeamRepository extends JpaRepository<AwayTeam, Long> {
}
