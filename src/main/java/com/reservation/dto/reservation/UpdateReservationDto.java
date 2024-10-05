package com.reservation.dto.reservation;

import com.reservation.type.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationDto {

    private ReservationStatus status;
    private Integer visitorNum;
    private boolean isVisited;
    private LocalDateTime reservationDate;

}
