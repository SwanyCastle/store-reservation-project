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
    REVIEW_ALREADY_EXISTS("리뷰가 이미 존재 합니다."),
    REVIEW_NO_AUTHORIZATION("리뷰 작성할 권한이 없습니다."),
    RESERVATION_NOT_FOUND("예약이 존재하지 않습니다."),
    RESERVATION_ALREADY_EXISTS("예약이 이미 존재 합니다."),
    RESERVATION_IMPOSSIBLE("예약할 수 없습니다."),
    RESERVATION_CAPACITY_OVER("수용인원 초과로 예약할 수 없습니다."),
    RESERVATION_LATE_TIME("방문 10분 전 시간만 예약할 수 있습니다."),
    RESERVATION_MEMBER_UNMATCHED("예약자와 일치하지 않습니다."),
    RESERVATION_DATE_UNMATCHED("예약한 날짜와 맞지 않습니다."),
    RESERVATION_VISIT_TIME_OVER("예약 도착시간이 지났습니다. 예약이 거절되었습니다."),
    STORE_NOT_FOUND("점포가 존재하지 않습니다."),
    STORE_ALREADY_EXISTS("점포가 이미 존재 합니다."),
    PASSWORD_UNMATCHED("비밀번호가 일치하지 않습니다."),
    ADDRESS_NOT_FOUND("주소를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS("이미 존재하는 사용자 입니다.");

    private final String description;
}
