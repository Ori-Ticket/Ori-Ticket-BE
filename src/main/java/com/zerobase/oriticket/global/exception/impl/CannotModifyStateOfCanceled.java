package com.zerobase.oriticket.global.exception.impl;

import com.zerobase.oriticket.global.constants.TransactionExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class CannotModifyStateOfCanceled extends AbstractException {
    @Override
    public int getErrorCode() {
        return TransactionExceptionStatus.CANNOT_MODIFY_STATE_OF_CANCELED.getCode();
    }

    @Override
    public String getMessage() {
        return TransactionExceptionStatus.CANNOT_MODIFY_STATE_OF_CANCELED.getMessage();
    }
}
