package com.zerobase.oriticket.domain.transaction.service;

import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCanceled;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCompleted;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFound;
import com.zerobase.oriticket.domain.transaction.dto.TransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.TransactionResponse;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUpdateService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;

    public TransactionResponse updateToReceived(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new TransactionNotFound());

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.RECEIVED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToCompleted(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new TransactionNotFound());

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.COMPLETED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToCanceled(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new TransactionNotFound());

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.CANCELED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public TransactionResponse updateToReported(TransactionRequest.UpdateStatus request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new TransactionNotFound());

        validateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.REPORTED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public void validateStatus(TransactionStatus status){
        if(status == TransactionStatus.CANCELED)
            throw new CannotModifyStateOfCanceled();
        if(status == TransactionStatus.COMPLETED)
            throw new CannotModifyStateOfCompleted();
    }
}
