package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.SportsRequest;
import com.zerobase.oriticket.domain.post.dto.SportsResponse;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
import com.zerobase.oriticket.global.exception.impl.post.CannotDeleteSportsExistTicket;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportsService {

    private final SportsRepository sportsRepository;
    private final TicketRepository ticketRepository;

    public SportsResponse register(SportsRequest.Register request) {

        return SportsResponse.fromEntity(
                sportsRepository.save(request.toEntity())
        );
    }

    public SportsResponse get(Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new SportsNotFound());

        return SportsResponse.fromEntity(sports);
    }

    public Page<SportsResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Sports> transactionDocuments = sportsRepository.findAll(pageable);

        return transactionDocuments.map(SportsResponse::fromEntity);
    }

    public void delete(Long sportsId){
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new SportsNotFound());

        boolean exists = ticketRepository.existsBySports(sports);

        if(exists) throw new CannotDeleteSportsExistTicket();

        sportsRepository.delete(sports);
    }

}
