package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.AwayTeamRequest;
import com.zerobase.oriticket.domain.post.dto.SportsRequest;
import com.zerobase.oriticket.domain.post.service.AwayTeamService;
import com.zerobase.oriticket.domain.post.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sports")
public class SportsController {

    private final SportsService sportsService;

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody SportsRequest.Register request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(sportsService.register(request));
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam("id") Long sportsId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(sportsService.get(sportsId));
    }
}
