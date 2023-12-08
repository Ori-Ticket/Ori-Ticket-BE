package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.PostRequest;
import com.zerobase.oriticket.domain.post.dto.PostResponse;
import com.zerobase.oriticket.domain.post.entity.*;
import com.zerobase.oriticket.domain.post.repository.*;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.post.*;
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
                .orElseThrow(() -> new SalePostNotFound());

        return PostResponse.fromEntity(salePost);
    }

    public void delete(Long postId) {
        Post salePost = postRepository.findById(postId)
                .orElseThrow(() -> new SalePostNotFound());

        boolean exists = transactionRepository.existsBySalePost(salePost);
        if (exists) throw new CannotDeletePostTransactionExist();

        postRepository.delete(salePost);
        ticketRepository.delete(salePost.getTicket());
    }

    public Ticket registerTicket(PostRequest.Register request) {

        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(() -> new SportsNotFound());

        Stadium stadium = stadiumRepository.findById(request.getSportsId())
                .orElseThrow(() -> new StadiumNotFound());

        AwayTeam awayTeam = awayTeamRepository.findById(request.getSportsId())
                .orElseThrow(() -> new AwayTeamNotFound());

        return ticketRepository.save(request.toEntityTicket(sports, stadium, awayTeam));
    }
}
