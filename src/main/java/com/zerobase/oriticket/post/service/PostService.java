package com.zerobase.oriticket.post.service;

import com.zerobase.oriticket.post.dto.PostResponse;
import com.zerobase.oriticket.post.dto.TicketRequest;
import com.zerobase.oriticket.post.entity.Ticket;

public interface PostService {

    // 판매 글 등록
    PostResponse registerPost(Long memberId, TicketRequest ticketRequest);

    // 판매 글 조회
    PostResponse getPost(Long postId);

    // 티켓 정보 등록
    Ticket registerTicket(TicketRequest ticketRequest);

}
