package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.RegisterPostRequest;
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

    public Post registerPost(RegisterPostRequest request) {

        // 멤버 객체 가져오도록 수정 예정

        Ticket ticket = registerTicket(request);

        return postRepository.save(request.toEntityPost(ticket));
    }

    public Post get(Long postId) {
        Post salePost = postRepository.findById(postId)
                .orElseThrow(SalePostNotFoundException::new);

        return salePost;
    }

    public Long delete(Long postId) {
        Post salePost = postRepository.findById(postId)
                .orElseThrow(SalePostNotFoundException::new);

        boolean exists = transactionRepository.existsBySalePost(salePost);
        if (exists){
            throw new CannotDeletePostExistTransactionException();
        }

        postRepository.delete(salePost);
        ticketRepository.delete(salePost.getTicket());

        return salePost.getSalePostId();
    }

    public Ticket registerTicket(RegisterPostRequest request) {

        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(SportsNotFoundException::new);

        Stadium stadium = stadiumRepository.findById(request.getSportsId())
                .orElseThrow(StadiumNotFoundException::new);

        AwayTeam awayTeam = awayTeamRepository.findById(request.getSportsId())
                .orElseThrow(AwayTeamNotFoundException::new);

        return ticketRepository.save(request.toEntityTicket(sports, stadium, awayTeam));
    }
}
