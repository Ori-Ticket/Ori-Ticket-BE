package com.zerobase.oriticket.domain.post.repository;

import com.zerobase.oriticket.domain.post.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
