package com.zerobase.oriticket.domain.report.repository;

import com.zerobase.oriticket.domain.report.entity.ReportTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportTransactionRepository extends JpaRepository<ReportTransaction, Long> {
}
