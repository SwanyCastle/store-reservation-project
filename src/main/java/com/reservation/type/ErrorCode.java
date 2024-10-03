package com.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    MEMBER_NOT_FOUND("사용자가 존재하지 않습니다."),
    PASSWORD_UNMATCHED("비밀번호가 일치하지 않습니다."),
    MEMBER_ALREADY_EXISTS("이미 존재하는 사용자 입니다.");

    private final String description;
}
