package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.SportsResponse;
import com.zerobase.oriticket.domain.post.dto.StadiumRequest;
import com.zerobase.oriticket.domain.post.dto.StadiumResponse;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<StadiumResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Stadium> transactionDocuments = stadiumRepository.findAll(pageable);

        return transactionDocuments.map(StadiumResponse::fromEntity);
    }
}
