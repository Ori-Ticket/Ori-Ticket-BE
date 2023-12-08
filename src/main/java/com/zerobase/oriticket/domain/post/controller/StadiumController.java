package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.StadiumRequest;
import com.zerobase.oriticket.domain.post.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadium")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody StadiumRequest.Register request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(stadiumService.register(request));
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam("id") Long stadiumId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(stadiumService.get(stadiumId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(stadiumService.getAll(page, size));
    }

    @GetMapping("/sports")
    public ResponseEntity<?> getBySportId(
            @RequestParam("id") Long sportsId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(stadiumService.getBySportsId(sportsId));
    }
}
