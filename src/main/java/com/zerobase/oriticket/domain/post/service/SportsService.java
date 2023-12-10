package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.RegisterSportsRequest;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
import com.zerobase.oriticket.global.exception.impl.post.CannotDeleteSportsExistTicketException;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsService {

    private final SportsRepository sportsRepository;
    private final TicketRepository ticketRepository;

    public Sports register(RegisterSportsRequest request) {

        return sportsRepository.save(request.toEntity());
    }

    public Sports get(Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(SportsNotFoundException::new);

        return sports;
    }

    public List<Sports> getAll() {

        return sportsRepository.findAll();
    }

    public Long delete(Long sportsId){
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(SportsNotFoundException::new);

        boolean exists = ticketRepository.existsBySports(sports);

        if(exists){
            throw new CannotDeleteSportsExistTicketException();
        }

        sportsRepository.delete(sports);

        return sports.getSportsId();
    }

}
