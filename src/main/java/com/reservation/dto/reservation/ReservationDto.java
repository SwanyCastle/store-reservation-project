package com.reservation.dto.reservation;

import com.reservation.domain.Member;
import com.reservation.domain.Reservation;
import com.reservation.domain.Store;
import com.reservation.type.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

public class ReservationDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private Long memberId;
        private Long shopId;
        private Integer visitorNum;
        private LocalDateTime reservationDate;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long reservationId;
        private Member member;
        private Store store;
        private Integer visitorNum;
        private boolean isVisited;
        private ReservationStatus status;
        private LocalDateTime reservationDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static ReservationDto.Response fromEntity(Reservation reservation) {
            return Response.builder()
                    .reservationId(reservation.getId())
                    .member(reservation.getMember())
                    .store(reservation.getStore())
                    .visitorNum(reservation.getVisitorNum())
                    .isVisited(reservation.isVisited())
                    .status(reservation.getStatus())
                    .reservationDate(reservation.getReservationDate())
                    .createdAt(reservation.getCreatedAt())
                    .updatedAt(reservation.getUpdatedAt())
                    .build();
        }
    }

}
