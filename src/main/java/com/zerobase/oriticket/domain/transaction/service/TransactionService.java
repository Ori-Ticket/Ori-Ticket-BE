package com.zerobase.oriticket.domain.transaction.service;

import com.zerobase.oriticket.domain.elasticsearch.post.repository.PostSearchRepository;
import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.RegisterTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.post.SalePostNotFoundException;
import com.zerobase.oriticket.global.exception.impl.transaction.AlreadyExistTransaction;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    private final PostRepository postRepository;
    private final PostSearchRepository postSearchRepository;

    private static final String STARTED_AT = "startedAt";

    public Transaction register(RegisterTransactionRequest request){

        Post salePost = postRepository.findById(request.getSalePostId())
                .orElseThrow(SalePostNotFoundException::new);

        // 멤버 유효성 체크
        boolean exists = transactionRepository.existsCanRegisterByStatus(salePost);

        if (!exists){
            throw new AlreadyExistTransaction();
        }

        Transaction transaction = transactionRepository.save(request.toEntity(salePost));

        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return transaction;
    }

    public Transaction get(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(TransactionNotFound::new);

        return transaction;
    }

    public Page<Transaction> getAll(int page, int size) {
        Sort sort = Sort.by(STARTED_AT).descending();

        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<Transaction> transactionDocuments = transactionRepository.findAll(pageable);

        return transactionDocuments;
    }

}
