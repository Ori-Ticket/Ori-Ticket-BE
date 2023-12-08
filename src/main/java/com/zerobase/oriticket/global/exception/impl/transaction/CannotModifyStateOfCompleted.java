package com.zerobase.oriticket.global.exception.impl.transaction;

import com.zerobase.oriticket.global.constants.TransactionExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class CannotModifyStateOfCompleted extends AbstractException {
    @Override
    public int getErrorCode() {
        return TransactionExceptionStatus.CANNOT_MODIFY_STATE_OF_COMPLETED.getCode();
    }

    @Override
    public String getMessage() {
        return TransactionExceptionStatus.CANNOT_MODIFY_STATE_OF_COMPLETED.getMessage();
    }
}
