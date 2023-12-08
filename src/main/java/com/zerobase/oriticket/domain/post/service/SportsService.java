package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.SportsRequest;
import com.zerobase.oriticket.domain.post.dto.SportsResponse;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.transaction.dto.TransactionResponse;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportsService {

    private final SportsRepository sportsRepository;

    public SportsResponse register(SportsRequest.Register request) {

        return SportsResponse.fromEntity(
                sportsRepository.save(request.toEntity())
        );
    }

    public SportsResponse get(Long awayTeamId) {
        Sports sports = sportsRepository.findById(awayTeamId)
                .orElseThrow(() -> new RuntimeException("스포츠 정보를 찾을 수 없습니다."));

        return SportsResponse.fromEntity(sports);
    }

    public Page<SportsResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Sports> transactionDocuments = sportsRepository.findAll(pageable);

        return transactionDocuments.map(SportsResponse::fromEntity);
    }
}
