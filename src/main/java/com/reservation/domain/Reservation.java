package com.reservation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Member member;

    private boolean isVisited;
    private LocalDateTime reservationDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
