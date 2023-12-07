package com.zerobase.oriticket.transaction.repository;

import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByStatus(TransactionStatus transactionStatus, Pageable pageable);
}
