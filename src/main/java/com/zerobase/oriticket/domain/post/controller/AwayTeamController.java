package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.AwayTeamRequest;
import com.zerobase.oriticket.domain.post.dto.PostRequest;
import com.zerobase.oriticket.domain.post.service.AwayTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/awayteam")
public class AwayTeamController {

    private final AwayTeamService awayTeamService;

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody AwayTeamRequest.Register request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(awayTeamService.register(request));
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam("id") Long awayTeamId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(awayTeamService.get(awayTeamId));
    }
}
