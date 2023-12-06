package com.zerobase.oriticket.elasticsearch.transaction.repository;

import com.zerobase.oriticket.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransactionSearchRepository extends ElasticsearchRepository<TransactionSearchDocument, Long> {
    Page<TransactionSearchDocument> findByStatus(TransactionStatus transactionStatus, Pageable pageable);
}
