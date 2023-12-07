package com.zerobase.oriticket.global.exception.impl;

import com.zerobase.oriticket.global.exception.AbstractException;
import com.zerobase.oriticket.global.constants.TransactionExceptionStatus;

public class TransactionNotFound extends AbstractException {
    @Override
    public int getErrorCode() {
        return TransactionExceptionStatus.TRANSACTION_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return TransactionExceptionStatus.TRANSACTION_NOT_FOUND.getMessage();
    }
}
