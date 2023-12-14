package com.zerobase.oriticket.post.controller;

import com.zerobase.oriticket.post.dto.TicketRequest;
import com.zerobase.oriticket.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.registerPost(1L, ticketRequest));
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("id") Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(postId));
    }

}
