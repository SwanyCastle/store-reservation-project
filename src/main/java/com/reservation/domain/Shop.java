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
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shopName;
    private String address;

    @ManyToOne
    private Member owner;

    private boolean isReservationPossible;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
