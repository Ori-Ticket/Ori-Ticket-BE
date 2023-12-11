package com.zerobase.oriticket.global.exception.impl.transaction;

import com.zerobase.oriticket.global.constants.TransactionExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class AlreadyExistTransactionException extends AbstractException {
    @Override
    public int getErrorCode() {
        return TransactionExceptionStatus.AlREADY_EXIST_TRANSACTION.getCode();
    }

    @Override
    public String getMessage() {
        return TransactionExceptionStatus.AlREADY_EXIST_TRANSACTION.getMessage();
    }
}
