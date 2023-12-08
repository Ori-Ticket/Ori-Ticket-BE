package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class CannotDeletePostTransactionExist extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.CANNOT_DELETE_POST_TRANSACTION_EXIST.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.CANNOT_DELETE_POST_TRANSACTION_EXIST.getMessage();
    }
}
