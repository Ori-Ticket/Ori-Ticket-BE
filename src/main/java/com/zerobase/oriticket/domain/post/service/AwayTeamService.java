package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.AwayTeamRequest;
import com.zerobase.oriticket.domain.post.dto.AwayTeamResponse;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.AwayTeamRepository;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwayTeamService {

    private final AwayTeamRepository awayTeamRepository;
    private final SportsRepository sportsRepository;

    public AwayTeamResponse register(AwayTeamRequest.Register request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new RuntimeException("스포츠 정보를 찾을 수 없습니다."));

        return AwayTeamResponse.fromEntity(
                awayTeamRepository.save(request.toEntity(sports))
        );
    }

    public AwayTeamResponse get(Long awayTeamId) {
        AwayTeam awayTeam = awayTeamRepository.findById(awayTeamId)
                .orElseThrow(() -> new RuntimeException("어웨이 팀 정보를 찾을 수 없습니다."));

        return AwayTeamResponse.fromEntity(awayTeam);
    }
}
