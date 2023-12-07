package com.zerobase.oriticket.elasticsearch.transaction.service;

import com.zerobase.oriticket.elasticsearch.transaction.dto.TransactionSearchResponse;
import com.zerobase.oriticket.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionSearchService {

    private final TransactionSearchRepository transactionSearchRepository;

    private String STARTED_AT = "startedAt";
    private final Sort sort = Sort.by(STARTED_AT).descending();

    public Page<TransactionSearchResponse> searchByStatus(String status, int page, int size){

        TransactionStatus transactionStatus = TransactionStatus.valueOf(status.toUpperCase());

        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<TransactionSearchDocument> transactionSearchDocuments = transactionSearchRepository.findByStatus(transactionStatus, pageable);

        return transactionSearchDocuments.map(TransactionSearchResponse::fromEntity);
    }

}
