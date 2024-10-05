package com.reservation.exception;

import com.reservation.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StoreException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public StoreException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
