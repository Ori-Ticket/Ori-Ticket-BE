package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.PostRequest;
import com.zerobase.oriticket.domain.post.dto.PostResponse;
import com.zerobase.oriticket.domain.post.entity.*;
import com.zerobase.oriticket.domain.post.repository.*;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final TransactionRepository transactionRepository;
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
        Post salePost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 글이 존재하지 않습니다."));

        return PostResponse.fromEntity(salePost);
    }

    public void delete(Long postId) {
        Post salePost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 글이 존재하지 않습니다."));

        boolean exists = transactionRepository.existsBySalePost(salePost);
        if (exists) throw new RuntimeException("생성된 거래가 있다면 판매글 삭제를 할 수 없습니다.");

        postRepository.delete(salePost);
        ticketRepository.delete(salePost.getTicket());
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
