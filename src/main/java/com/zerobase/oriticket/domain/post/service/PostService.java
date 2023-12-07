package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.dto.PostRequest;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Ticket;
import com.zerobase.oriticket.domain.post.dto.PostResponse;
import com.zerobase.oriticket.domain.post.dto.TicketRequest;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.repository.AwayTeamRepository;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.post.repository.SportsRepository;
import com.zerobase.oriticket.domain.post.repository.StadiumRepository;
import com.zerobase.oriticket.domain.post.repository.TicketRepository;
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
                postRepository.save(ticketRequest.to);
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 글이 존재하지 않습니다."));
        return PostResponse.fromEntity(post);
    }

    public Ticket registerTicket(PostRequest.Register request) {

        Sports sports = sportsRepository.findById(ticketRequest.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 스포츠가 존재하지 않습니다."));

        Stadium stadium = stadiumRepository.findById(ticketRequest.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 경기장이 존재하지 않습니다."));

        AwayTeam awayTeam = awayTeamRepository.findById(ticketRequest.getSportsId())
                .orElseThrow(() -> new RuntimeException("해당 팀이 존재하지 않습니다."));

        return ticketRepository.save(Ticket.builder()
                .sports(sports)
                .stadium(stadium)
                .awayTeam(awayTeam)
                .quantity(ticketRequest.getQuantity())
                .salePrice(ticketRequest.getSalePrice())
                .originalPrice(ticketRequest.getOriginalPrice())
                .expirationAt(ticketRequest.getExpirationAt())
                .isSuccessive(ticketRequest.isSuccessive())
                .seatInfo(ticketRequest.getSeatInfo())
                .note(ticketRequest.getNote())
                .build());
    }


}
