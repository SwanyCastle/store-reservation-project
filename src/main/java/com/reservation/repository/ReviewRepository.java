package com.reservation.repository;

import com.reservation.domain.Member;
import com.reservation.domain.Review;
import com.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStore(Store store);

    List<Review> findAllByMember(Member member);

    Optional<Review> findByMemberAndStore(Member member, Store store);

    @Query("SELECT COALESCE(SUM(r.rating), 0) FROM Review r WHERE r.store = :store")
    Double avgRatingByStore(@Param("store") Store store);
}
