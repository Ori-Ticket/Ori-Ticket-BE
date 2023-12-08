package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.StadiumRequest;
import com.zerobase.oriticket.domain.post.dto.StadiumResponse;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.StadiumRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
import com.zerobase.oriticket.global.exception.impl.post.CannotDeleteStadiumExistTicket;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFound;
import com.zerobase.oriticket.global.exception.impl.post.StadiumNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final SportsRepository sportsRepository;
    private final TicketRepository ticketRepository;

    public StadiumResponse register(StadiumRequest.Register request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new SportsNotFound());

        return StadiumResponse.fromEntity(
                stadiumRepository.save(request.toEntity(sports))
        );
    }

    public StadiumResponse get(Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new StadiumNotFound());

        return StadiumResponse.fromEntity(stadium);
    }

    public Page<StadiumResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Stadium> transactionDocuments = stadiumRepository.findAll(pageable);

        return transactionDocuments.map(StadiumResponse::fromEntity);
    }

    public List<StadiumResponse> getBySportsId(Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new SportsNotFound());

        List<Stadium> stadiums = stadiumRepository.findBySports(sports);

        return stadiums.stream()
                .map(StadiumResponse::fromEntity)
                .toList();
    }

    public void delete(Long stadiumId){
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new StadiumNotFound());

        boolean exists = ticketRepository.existsByStadium(stadium);

        if(exists) throw new CannotDeleteStadiumExistTicket();

        stadiumRepository.delete(stadium);
    }
}
