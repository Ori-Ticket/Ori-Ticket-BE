package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.AwayTeamRequest;
import com.zerobase.oriticket.domain.post.dto.AwayTeamResponse;
import com.zerobase.oriticket.domain.post.dto.StadiumRequest;
import com.zerobase.oriticket.domain.post.dto.StadiumResponse;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.repository.AwayTeamRepository;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final SportsRepository sportsRepository;

    public StadiumResponse register(StadiumRequest.Register request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new RuntimeException("스포츠 정보를 찾을 수 없습니다."));

        return StadiumResponse.fromEntity(
                stadiumRepository.save(request.toEntity(sports))
        );
    }

    public StadiumResponse get(Long awayTeamId) {
        Stadium stadium = stadiumRepository.findById(awayTeamId)
                .orElseThrow(() -> new RuntimeException("경기장 정보를 찾을 수 없습니다."));

        return StadiumResponse.fromEntity(stadium);
    }
}
