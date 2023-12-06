package com.zerobase.oriticket.transaction.service;

import com.zerobase.oriticket.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.transaction.dto.TransactionRequest;
import com.zerobase.oriticket.transaction.dto.TransactionResponse;
import com.zerobase.oriticket.transaction.entity.Transaction;
import com.zerobase.oriticket.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUpdateService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;

    public TransactionResponse updateToReceived(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.RECEIVED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToCompleted(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.COMPLETED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToCanceled(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.CANCELED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToReported(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("해당 거래 없음."));

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.REPORTED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public void validateStatus(TransactionStatus status){
        if(status == TransactionStatus.CANCELED)
            throw new RuntimeException("취소된 거래는 상태를 변경할 수 없습니다.");
        if(status == TransactionStatus.COMPLETED)
            throw new RuntimeException("완료된 거래는 상태를 변경할 수 없습니다.");
    }
}
