package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.PostRequest;
import com.zerobase.oriticket.domain.post.dto.PostResponse;
import com.zerobase.oriticket.domain.post.entity.*;
import com.zerobase.oriticket.domain.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TicketRepository ticketRepository;
    private final SportsRepository sportsRepository;
    private final StadiumRepository stadiumRepository;
    private final AwayTeamRepository awayTeamRepository;

    public PostResponse registerPost(PostRequest.Register request) {

        // 멤버 객체 가져오도록 수정 예정

        Ticket ticket = registerTicket(request);

        return PostResponse.fromEntity(
                postRepository.save(request.toEntityPost(ticket))
        );
    }

    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 글이 존재하지 않습니다."));

        return PostResponse.fromEntity(post);
    }

    public Ticket registerTicket(PostRequest.Register request) {

        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 스포츠가 존재하지 않습니다."));

        Stadium stadium = stadiumRepository.findById(request.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 경기장이 존재하지 않습니다."));

        AwayTeam awayTeam = awayTeamRepository.findById(request.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 팀이 존재하지 않습니다."));

        return ticketRepository.save(request.toEntityTicket(sports, stadium, awayTeam));
    }


}
