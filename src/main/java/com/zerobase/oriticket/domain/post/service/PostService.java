package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.elasticsearch.post.entity.PostSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.post.repository.PostSearchRepository;
import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.dto.RegisterPostRequest;
import com.zerobase.oriticket.domain.post.dto.UpdateStatusToReportedPostRequest;
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
    private final PostSearchRepository postSearchRepository;
    private final TicketRepository ticketRepository;
    private final SportsRepository sportsRepository;
    private final StadiumRepository stadiumRepository;
    private final AwayTeamRepository awayTeamRepository;

    public Post registerPost(RegisterPostRequest request) {

        // 멤버 객체 가져오도록 수정 예정

        Ticket ticket = registerTicket(request);

        Post post = postRepository.save(request.toEntityPost(ticket));

        postSearchRepository.save(PostSearchDocument.fromEntity(post));

        return post;
    }

    public Post get(Long salePostId) {
        Post salePost = postRepository.findById(salePostId)
                .orElseThrow(SalePostNotFoundException::new);

        return salePost;
    }

    public Long delete(Long salePostId) {
        Post salePost = postRepository.findById(salePostId)
                .orElseThrow(SalePostNotFoundException::new);

        boolean exists = transactionRepository.existsBySalePost(salePost);
        if (exists){
            throw new CannotDeletePostExistTransactionException();
        }

        postRepository.delete(salePost);
        ticketRepository.delete(salePost.getTicket());

        postSearchRepository.delete(PostSearchDocument.fromEntity(salePost));

        return salePost.getSalePostId();
    }

    public Ticket registerTicket(RegisterPostRequest request) {

        Sports sports = sportsRepository.findById(request.getSportsId())
                .orElseThrow(SportsNotFoundException::new);

        Stadium stadium = stadiumRepository.findById(request.getStadiumId())
                .orElseThrow(StadiumNotFoundException::new);

        AwayTeam awayTeam = awayTeamRepository.findById(request.getAwayTeamId())
                .orElseThrow(AwayTeamNotFoundException::new);

        return ticketRepository.save(request.toEntityTicket(sports, stadium, awayTeam));
    }

    public Post updateToReported(UpdateStatusToReportedPostRequest request) {
        Post salePost = postRepository.findById(request.getSalePostId())
                .orElseThrow(SalePostNotFoundException::new);

        validateCanUpdateToReportedStatus(salePost.getSaleStatus());

        salePost.updateToReported();
        return postRepository.save(salePost);
    }

    public void validateCanUpdateToReportedStatus(SaleStatus status){
        if(status == SaleStatus.REPORTED)
            throw new CannotModifyPostStateOfReportedException();
        if(status == SaleStatus.SOLD)
            throw new CannotModifyPostStateOfSoldException();
    }
}
