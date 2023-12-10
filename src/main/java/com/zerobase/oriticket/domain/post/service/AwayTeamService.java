package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.RegisterAwayTeamRequest;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.repository.AwayTeamRepository;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
import com.zerobase.oriticket.global.exception.impl.post.AwayTeamNotFoundException;
import com.zerobase.oriticket.global.exception.impl.post.CannotDeleteAwayTeamExistTicketException;
import com.zerobase.oriticket.global.exception.impl.post.SportsNotFoundException;
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
    private final TicketRepository ticketRepository;

    public AwayTeam register(RegisterAwayTeamRequest request) {
        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(SportsNotFoundException::new);


        return awayTeamRepository.save(request.toEntity(sports));
    }

    public AwayTeam get(Long awayTeamId) {
        AwayTeam awayTeam = awayTeamRepository.findById(awayTeamId)
                .orElseThrow(AwayTeamNotFoundException::new);

        return awayTeam;
    }

    public List<AwayTeam> getAll() {

        return awayTeamRepository.findAll();
    }

    public List<AwayTeam> getBySportsId(Long sportsId) {

        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(SportsNotFoundException::new);

        return awayTeamRepository.findBySports(sports);
    }

    public Long delete(Long awayTeamId){
        AwayTeam awayTeam = awayTeamRepository.findById(awayTeamId)
                .orElseThrow(AwayTeamNotFoundException::new);

        boolean exists = ticketRepository.existsByAwayTeam(awayTeam);

        if(exists){
            throw new CannotDeleteAwayTeamExistTicketException();
        }

        awayTeamRepository.delete(awayTeam);

        return awayTeam.getAwayTeamId();
    }
}
