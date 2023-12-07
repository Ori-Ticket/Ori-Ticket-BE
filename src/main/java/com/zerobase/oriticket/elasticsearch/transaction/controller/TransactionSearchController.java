package com.zerobase.oriticket.elasticsearch.transaction.controller;

import com.zerobase.oriticket.elasticsearch.transaction.service.TransactionSearchService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionSearchController {

    private final TransactionSearchService transactionSearchService;

    @GetMapping("/search/status")
    public ResponseEntity<?> searchByStatus(
            @RequestParam("value") String status,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(transactionSearchService.searchByStatus(status, page, size));
    }
}
