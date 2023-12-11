package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class CannotModifyPostStateOfReportedException extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.CANNOT_MODIFY_POST_STATE_OF_REPORTED.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.CANNOT_MODIFY_POST_STATE_OF_REPORTED.getMessage();
    }
}
