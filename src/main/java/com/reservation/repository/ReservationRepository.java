package com.reservation.repository;

import com.reservation.domain.Member;
import com.reservation.domain.Reservation;
import com.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByStore(Store store);

    Optional<Reservation> findByMemberAndStoreAndReservationDate(
            Member member, Store store, LocalDateTime reservationDate
    );

    Optional<Reservation> findByMemberAndStore(
            Member member, Store store
    );

    @Query("SELECT COALESCE(SUM(r.visitorNum), 0) FROM Reservation r WHERE r.store = :store")
    Integer sumVisitorNumByStore(@Param("store") Store store);

    @Query("SELECT r FROM Reservation r WHERE r.store = :store AND DATE(r.reservationDate) = :date")
    List<Reservation> findReservationsByStoreAndDate(
            @Param("store") Store store,
            @Param("date") LocalDate date
    );
}
