package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.RegisterSportsRequest;
import com.zerobase.oriticket.domain.post.dto.SportsResponse;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sports")
public class SportsController {

    private final SportsService sportsService;

    @PostMapping
    public ResponseEntity<SportsResponse> register(
            @RequestBody RegisterSportsRequest request
    ){
        Sports sports = sportsService.register(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SportsResponse.fromEntity(sports));
    }

    @GetMapping
    public ResponseEntity<SportsResponse> get(
            @RequestParam("id") Long sportsId
    ){
        Sports sports = sportsService.get(sportsId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SportsResponse.fromEntity(sports));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<SportsResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<Sports> sports = sportsService.getAll(page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(sports.map(SportsResponse::fromEntity));
    }

    @DeleteMapping
    public void delete(
            @RequestParam("id") Long sportsId
    ){
        sportsService.delete(sportsId);
    }
}
