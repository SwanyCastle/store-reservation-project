package com.reservation.repository;

import com.reservation.domain.Member;
import com.reservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findAllByOrderByStoreNameAsc();

    List<Store> findAllByOrderByRatingDesc();

    Optional<Store> findByStoreNameAndMember(String storeName, Member member);


}
