package com.zerobase.oriticket.domain.transaction.service;

import com.zerobase.oriticket.domain.elasticsearch.post.repository.PostSearchRepository;
import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusToReceivedTransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCanceled;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfCompleted;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyStateOfReported;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUpdateService {

    private final TransactionRepository transactionRepository;
    private final PostRepository postRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    private final PostSearchRepository postSearchRepository;

    public Transaction updateToReceived(UpdateStatusToReceivedTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToReceived(request.getPayAmount());
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCompleted(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToCompleted();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCanceled(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToCanceled();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToReported(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFound::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToReported();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transactionRepository.save(transaction);
    }

    public void validateCanUpdateStatus(TransactionStatus status){
        if(status == TransactionStatus.CANCELED)
            throw new CannotModifyStateOfCanceled();
        if(status == TransactionStatus.COMPLETED)
            throw new CannotModifyStateOfCompleted();
        if(status == TransactionStatus.REPORTED)
            throw new CannotModifyStateOfReported();
    }
}
