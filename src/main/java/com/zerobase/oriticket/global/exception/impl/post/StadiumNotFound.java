package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class StadiumNotFound extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.STADIUM_NOT_FOUND.getCode();

    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.STADIUM_NOT_FOUND.getMessage();

    }
}
