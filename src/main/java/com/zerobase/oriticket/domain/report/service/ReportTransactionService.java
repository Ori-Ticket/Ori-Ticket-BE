package com.zerobase.oriticket.domain.report.service;

import com.zerobase.oriticket.domain.report.constants.ReportReactStatus;
import com.zerobase.oriticket.domain.report.dto.RegisterReportTransactionRequest;
import com.zerobase.oriticket.domain.report.dto.UpdateReportRequest;
import com.zerobase.oriticket.domain.report.entity.ReportTransaction;
import com.zerobase.oriticket.domain.report.repository.ReportTransactionRepository;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.constants.ReportExceptionStatus;
import com.zerobase.oriticket.global.constants.TransactionExceptionStatus;
import com.zerobase.oriticket.global.exception.impl.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportTransactionService {

    private final ReportTransactionRepository reportTransactionRepository;
    private final TransactionRepository transactionRepository;

    public ReportTransaction register(Long transactionId, RegisterReportTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(TransactionExceptionStatus.TRANSACTION_NOT_FOUND.getCode(),
                        TransactionExceptionStatus.TRANSACTION_NOT_FOUND.getMessage()));

        return reportTransactionRepository.save(request.toEntity(transaction));
    }

    public ReportTransaction updateToReacted(Long reportTransactionId, UpdateReportRequest request) {
        ReportTransaction reportTransaction = reportTransactionRepository.findById(reportTransactionId)
                .orElseThrow(() -> new CustomException(ReportExceptionStatus.REPORT_TRANSACTION_NOT_FOUND.getCode(),
                        ReportExceptionStatus.REPORT_TRANSACTION_NOT_FOUND.getMessage()));

        reportTransaction.setStatus(ReportReactStatus.REACTED);
        reportTransaction.setReactedAt(LocalDateTime.now());
        reportTransaction.setNote(request.getNote());

        return reportTransactionRepository.save(reportTransaction);
    }
}
