package com.reservation.exception;

import com.reservation.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class KakaoException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public KakaoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
