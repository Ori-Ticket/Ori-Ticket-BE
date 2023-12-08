package com.zerobase.oriticket.global.exception.impl;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class SalePostNotFound extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.SALE_POST_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.SALE_POST_NOT_FOUND.getMessage();
    }
}
