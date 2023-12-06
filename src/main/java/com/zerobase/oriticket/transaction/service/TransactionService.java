package com.zerobase.oriticket.transaction.service;

import com.zerobase.oriticket.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.global.exception.impl.TransactionNotFound;
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

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    private String STARTED_AT = "startedAt";

    public TransactionResponse register(TransactionRequest.Register request){
        // 판매 글 유효성 체크

        // 멤버 유효성 체크

        Transaction transaction = transactionRepository.save(request.toEntity(request.getSalePostId(), request.getMemberId()));

        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        return TransactionResponse.fromEntity(transaction);
    }

    public TransactionResponse get(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFound());

        return TransactionResponse.fromEntity(transaction);
    }

    public Page<TransactionResponse> getAll(int page, int size) {
        Sort sort = Sort.by(STARTED_AT).descending();

        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<Transaction> transactionDocuments = transactionRepository.findAll(pageable);

        return transactionDocuments.map(TransactionResponse::fromEntity);
    }
}
