package com.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    MEMBER_NOT_FOUND("사용자가 존재하지 않습니다."),
    REVIEW_NOT_FOUND("리뷰가 존재하지 않습니다."),
    RESERVATION_NOT_FOUND("예약이 존재하지 않습니다."),
    RESERVATION_IMPOSSIBLE("예약할 수 없습니다."),
    RESERVATION_VISIT_TIME_OVER("예약 도착시간이 지났습니다. 예약이 거절되었습니다."),
    STORE_NOT_FOUND("점포가 존재하지 않습니다."),
    PASSWORD_UNMATCHED("비밀번호가 일치하지 않습니다."),
    MEMBER_ALREADY_EXISTS("이미 존재하는 사용자 입니다.");

    private final String description;
}
