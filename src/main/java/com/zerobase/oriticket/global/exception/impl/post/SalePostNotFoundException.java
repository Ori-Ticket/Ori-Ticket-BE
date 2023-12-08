package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class SalePostNotFoundException extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.SALE_POST_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.SALE_POST_NOT_FOUND.getMessage();
    }
}
