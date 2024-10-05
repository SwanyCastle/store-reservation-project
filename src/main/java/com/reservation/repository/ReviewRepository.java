package com.reservation.repository;

import com.reservation.domain.Member;
import com.reservation.domain.Review;
import com.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStore(Store store);

    List<Review> findAllByMember(Member member);

}
