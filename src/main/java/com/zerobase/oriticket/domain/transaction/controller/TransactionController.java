package com.zerobase.oriticket.domain.transaction.controller;

import com.zerobase.oriticket.domain.transaction.dto.TransactionRequest;
import com.zerobase.oriticket.domain.transaction.service.TransactionService;
import com.zerobase.oriticket.domain.transaction.service.TransactionUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionUpdateService transactionUpdateService;


    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody TransactionRequest.Register request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.register(request));
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam("id") Long transactionId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.get(transactionId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok(transactionService.getAll(page, size));
    }

    @PatchMapping("/receive")
    public ResponseEntity<?> updateToReceived(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionUpdateService.updateToReceived(request));
    }

    @PatchMapping("/completion")
    public ResponseEntity<?> updateToCompleted(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionUpdateService.updateToCompleted(request));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> updateToCanceled(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionUpdateService.updateToCanceled(request));
    }

    @PatchMapping("/report")
    public ResponseEntity<?> updateToReported(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionUpdateService.updateToReported(request));
    }

}
