package com.zerobase.oriticket.domain.transaction.service;

import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCanceled;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCompleted;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUpdateService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;

    public Transaction updateToReceived(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.RECEIVED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCompleted(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.COMPLETED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCanceled(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.CANCELED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToReported(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatus(TransactionStatus.REPORTED);
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public void validateCanUpdateStatus(TransactionStatus status){
        if(status == TransactionStatus.CANCELED)
            throw new CannotModifyStateOfCanceled();
        if(status == TransactionStatus.COMPLETED)
            throw new CannotModifyStateOfCompleted();
    }
}
