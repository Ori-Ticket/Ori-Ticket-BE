package com.zerobase.oriticket.post.repository;

import com.zerobase.oriticket.post.entity.Sports;
import com.zerobase.oriticket.post.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {
}
