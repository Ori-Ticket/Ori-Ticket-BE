package com.zerobase.oriticket.global.exception.impl.post;

import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class AwayTeamNotFoundException extends AbstractException {
    @Override
    public int getErrorCode() {
        return PostExceptionStatus.AWAY_TEAM_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return PostExceptionStatus.AWAY_TEAM_NOT_FOUND.getMessage();
    }
}
