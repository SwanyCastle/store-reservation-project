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

    private Integer visitorNum;
    private LocalDateTime reservationDate;

}
