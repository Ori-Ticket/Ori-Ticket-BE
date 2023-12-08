package com.zerobase.oriticket.domain.post.controller;

import com.zerobase.oriticket.domain.post.dto.RegisterStadiumRequest;
import com.zerobase.oriticket.domain.post.dto.StadiumResponse;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadium")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping
    public ResponseEntity<StadiumResponse> register(
            @RequestBody RegisterStadiumRequest request
    ){
        Stadium stadium = stadiumService.register(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(StadiumResponse.fromEntity(stadium));
    }

    @GetMapping
    public ResponseEntity<StadiumResponse> get(
            @RequestParam("id") Long stadiumId
    ){
        Stadium stadium = stadiumService.get(stadiumId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(StadiumResponse.fromEntity(stadium));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<StadiumResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<Stadium> stadiums = stadiumService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(stadiums.map(StadiumResponse::fromEntity));
    }

    @GetMapping("/sports")
    public ResponseEntity<List<StadiumResponse>> getBySportId(
            @RequestParam("id") Long sportsId
    ){
        List<Stadium> stadiums = stadiumService.getBySportsId(sportsId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        stadiums.stream()
                        .map(StadiumResponse::fromEntity)
                        .toList()
                );
    }

    @DeleteMapping
    public void delete(
            @RequestParam("id") Long stadiumId
    ){
        stadiumService.delete(stadiumId);
    }
}
