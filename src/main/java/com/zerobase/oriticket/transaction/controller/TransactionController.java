package com.zerobase.oriticket.transaction.controller;

import com.zerobase.oriticket.transaction.dto.TransactionRequest;
import com.zerobase.oriticket.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

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
            @RequestParam("page") int page,
            @RequestParam("size") int size

    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getAll(page, size));
    }

    @GetMapping("/pend/list")
    public ResponseEntity<?> getPendStatus(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getPendStatus(page, size));
    }

    @GetMapping("/receive/list")
    public ResponseEntity<?> getReceiveStatus(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getReceiveStatus(page, size));
    }

    @PatchMapping("/receive")
    public ResponseEntity<?> updateToReceived(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.updateToReceived(request));
    }

    @GetMapping("/completion/list")
    public ResponseEntity<?> getCompletionStatus(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getCompletionStatus(page, size));
    }

    @PatchMapping("/completion")
    public ResponseEntity<?> updateToCompleted(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.updateToCompleted(request));
    }

    @GetMapping("/cancel/list")
    public ResponseEntity<?> getCancelStatus(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getCancelStatus(page, size));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> updateToCanceled(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.updateToCanceled(request));
    }

    @GetMapping("/report/list")
    public ResponseEntity<?> getReportStatus(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getReportStatus(page, size));
    }

    @PatchMapping("/report")
    public ResponseEntity<?> updateToReported(
            @RequestBody TransactionRequest.UpdateStatus request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.updateToReported(request));
    }

}
