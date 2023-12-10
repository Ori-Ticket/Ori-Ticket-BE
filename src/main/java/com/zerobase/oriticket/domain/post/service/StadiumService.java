package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.RegisterStadiumRequest;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.StadiumRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
import com.zerobase.oriticket.global.exception.impl.post.CannotDeleteStadiumExistTicketException;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFoundException;
import com.zerobase.oriticket.global.exception.impl.post.StadiumNotFoundException;
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

    public Stadium register(RegisterStadiumRequest request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(SportsNotFoundException::new);

        return stadiumRepository.save(request.toEntity(sports));
    }

    public Stadium get(Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(StadiumNotFoundException::new);

        return stadium;
    }

    public List<Stadium> getAll() {

        return stadiumRepository.findAll();
    }

    public List<Stadium> getBySportsId(Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(SportsNotFoundException::new);


        return stadiumRepository.findBySports(sports);
    }

    public Long delete(Long stadiumId){
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(StadiumNotFoundException::new);

        boolean exists = ticketRepository.existsByStadium(stadium);

        if(exists){
            throw new CannotDeleteStadiumExistTicketException();
        }

        stadiumRepository.delete(stadium);

        return stadium.getStadiumId();
    }
}
