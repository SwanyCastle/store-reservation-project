package com.reservation.repository;

import com.reservation.domain.Reservation;
import com.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByStore(Store store);

    Integer sumVisitorNumByStore(Store store);
}
