package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.AwayTeamRequest;
import com.zerobase.oriticket.domain.post.dto.AwayTeamResponse;
import com.zerobase.oriticket.domain.post.dto.SportsResponse;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.AwayTeamRepository;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.global.exception.impl.post.AwayTeamNotFound;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AwayTeamService {

    private final AwayTeamRepository awayTeamRepository;
    private final SportsRepository sportsRepository;

    public AwayTeamResponse register(AwayTeamRequest.Register request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new SportsNotFound());

        return AwayTeamResponse.fromEntity(
                awayTeamRepository.save(request.toEntity(sports))
        );
    }

    public AwayTeamResponse get(Long awayTeamId) {
        AwayTeam awayTeam = awayTeamRepository.findById(awayTeamId)
                .orElseThrow(() -> new AwayTeamNotFound());

        return AwayTeamResponse.fromEntity(awayTeam);
    }

    public Page<AwayTeamResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<AwayTeam> transactionDocuments = awayTeamRepository.findAll(pageable);

        return transactionDocuments.map(AwayTeamResponse::fromEntity);
    }

    public List<AwayTeamResponse> getBySportsId(Long sportsId) {

        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new SportsNotFound());

        List<AwayTeam> awayTeams = awayTeamRepository.findBySports(sports);

        return awayTeams.stream()
                .map(AwayTeamResponse::fromEntity)
                .toList();
    }

}
