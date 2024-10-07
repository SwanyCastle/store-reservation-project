package com.reservation.dto.reservation;

import com.reservation.domain.Reservation;
import com.reservation.dto.member.MemberDto;
import com.reservation.dto.store.StoreDto;
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
        private Long storeId;
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
        private MemberDto member;
        private StoreDto.Response store;
        private Integer visitorNum;
        private boolean isVisited;
        private ReservationStatus status;
        private LocalDateTime reservationDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static ReservationDto.Response fromEntity(Reservation reservation) {
            return Response.builder()
                    .reservationId(reservation.getId())
                    .member(MemberDto.fromEntity(reservation.getMember()))
                    .store(StoreDto.Response.fromEntity(reservation.getStore()))
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
