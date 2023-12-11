package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class CannotDeleteSportsExistTicketException extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.CANNOT_DELETE_SPORTS_EXIST_TICKET.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.CANNOT_DELETE_SPORTS_EXIST_TICKET.getMessage();
    }
}
