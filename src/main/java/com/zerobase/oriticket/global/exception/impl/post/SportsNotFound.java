package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class SportsNotFound extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.SPORTS_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.SPORTS_NOT_FOUND.getMessage();
    }
}
