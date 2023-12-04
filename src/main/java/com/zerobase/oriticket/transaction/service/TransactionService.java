package com.zerobase.oriticket.transaction.service;

import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.transaction.dto.TransactionRequest;
import com.zerobase.oriticket.transaction.dto.TransactionResponse;
import com.zerobase.oriticket.transaction.entity.Transaction;
import com.zerobase.oriticket.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private Sort sort = Sort.by("startedAt").descending();

    public TransactionResponse register(TransactionRequest.Register request){
        // 판매 글 유효성 체크

        // 멤버 유효성 체크

        return TransactionResponse.fromEntity(
                transactionRepository.save(request.toEntity(1L, 1L))
        );
    }

    public TransactionResponse get(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        return TransactionResponse.fromEntity(transaction);
    }

    public Page<TransactionResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findAll(pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public Page<TransactionResponse> getPendStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.PENDING, pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public Page<TransactionResponse> getReceiveStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.RECEIVED, pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public TransactionResponse updateToReceived(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        TransactionStatus status = transaction.getStatus();

        if(status == TransactionStatus.CANCELED)
            throw new RuntimeException("취소된 거래는 상태를 변경할 수 없습니다.");
        else if(status == TransactionStatus.COMPLETED)
            throw new RuntimeException("완료된 거래는 상태를 변경할 수 없습니다.");

        transaction.updateStatus(TransactionStatus.RECEIVED);

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public Page<TransactionResponse> getCompletionStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.COMPLETED, pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public TransactionResponse updateToCompleted(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        TransactionStatus status = transaction.getStatus();

        if(status == TransactionStatus.CANCELED)
            throw new RuntimeException("취소된 거래는 상태를 변경할 수 없습니다.");
        else if(status == TransactionStatus.COMPLETED)
            throw new RuntimeException("완료된 거래는 상태를 변경할 수 없습니다.");

        transaction.updateStatus(TransactionStatus.COMPLETED);

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public Page<TransactionResponse> getCancelStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.CANCELED, pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public TransactionResponse updateToCanceled(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        TransactionStatus status = transaction.getStatus();

        if(status == TransactionStatus.CANCELED)
            throw new RuntimeException("취소된 거래는 상태를 변경할 수 없습니다.");
        else if(status == TransactionStatus.COMPLETED)
            throw new RuntimeException("완료된 거래는 상태를 변경할 수 없습니다.");

        transaction.updateStatus(TransactionStatus.CANCELED);

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public Page<TransactionResponse> getReportStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.REPORTED, pageable);

        return transactions.map(TransactionResponse::fromEntity);
    }

    public TransactionResponse updateToReported(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        TransactionStatus status = transaction.getStatus();

        if(status == TransactionStatus.CANCELED)
            throw new RuntimeException("취소된 거래는 상태를 변경할 수 없습니다.");
        else if(status == TransactionStatus.COMPLETED)
            throw new RuntimeException("완료된 거래는 상태를 변경할 수 없습니다.");

        transaction.updateStatus(TransactionStatus.REPORTED);

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }
}
