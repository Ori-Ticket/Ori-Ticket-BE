package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.PostResponse;
import com.zerobase.oriticket.domain.post.dto.RegisterPostRequest;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> register(
            @RequestBody RegisterPostRequest request
    ){
        Post post = postService.registerPost(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(PostResponse.fromEntity(post));
    }

    @GetMapping
    public ResponseEntity<PostResponse> get(
            @RequestParam("id") Long postId
    ){
        Post post = postService.get(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(PostResponse.fromEntity(post));
    }

    @DeleteMapping
    private Long delete(
            @RequestParam("id") Long postId
    ){
        return postService.delete(postId);
    }

}
