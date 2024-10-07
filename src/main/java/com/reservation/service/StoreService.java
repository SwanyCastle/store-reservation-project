package com.reservation.service;

import com.reservation.domain.Member;
import com.reservation.domain.Store;
import com.reservation.dto.store.StoreDto;
import com.reservation.dto.store.UpdateStoreDto;
import com.reservation.exception.StoreException;
import com.reservation.repository.StoreRepository;
import com.reservation.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final MemberService memberService;
    private final KakaoMapService kakaoMapService;
    private final StoreRepository storeRepository;

    /**
     * 가게 정보 등록
     * @param request
     * @return StoreDto.Response
     */
    public StoreDto.Response createStore(StoreDto.Request request) {
        Member member = memberService.getMemberById(request.getMemberId());

        checkExistsStore(member, request.getStoreName());

        double[] distanceFromAddress = kakaoMapService.getDistanceFromAddress(request.getStoreAddress());


        return StoreDto.Response.fromEntity(
                storeRepository.save(
                        Store.builder()
                                .storeName(request.getStoreName())
                                .storeAddress(request.getStoreAddress())
                                .member(member)
                                .rating(0.0)
                                .capacityPerson(request.getCapacityPerson())
                                .latitude(distanceFromAddress[0])
                                .longitude(distanceFromAddress[1])
                                .build()
                )
        );
    }

    /**
     * 가게 중복 체크
     * @param member
     * @param storeName
     */
    public void checkExistsStore(Member member, String storeName) {
        Optional<Store> store = storeRepository.findByStoreNameAndMember(
                storeName, member
        );

        if (store.isPresent()) {
            throw new StoreException(ErrorCode.STORE_ALREADY_EXISTS);
        }
    }

    /**
     * 전체 가게 목록 조회
     * @return List<StoreDto.Response>
     */
    public List<StoreDto.Response> getStores() {
        List<Store> storeList = storeRepository.findAll();

        return storeList.stream()
                .map(StoreDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 가게 이름 가나다순으로 정렬해서 가게 목록 조회
     * @return List<StoreDto.Response>
     */
    public List<StoreDto.Response> getStoresSortedByName() {
        List<Store> storeList = storeRepository.findAllByOrderByStoreNameAsc();

        return storeList.stream()
                .map(StoreDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 가게 평점 순으로 정렬해서 가게 목록 조회
     * @return List<StoreDto.Response>
     */
    public List<StoreDto.Response> getStoresSortedByRating() {
        List<Store> storeList = storeRepository.findAllByOrderByRatingDesc();

        return storeList.stream()
                .map(StoreDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 위치에서 가게까지 거리순으로 가게 목록 조회
     * @param userLat
     * @param userLng
     * @return List<StoreDto.Response>
     */
    public List<StoreDto.Response> getStoresSortedByDistance(double userLat, double userLng) {
        List<Store> storeList = storeRepository.findAll();

        return storeList.stream()
                .sorted(Comparator.comparingDouble(store -> calculateDistance(
                        userLat, userLng, store.getLatitude(), store.getLongitude()
                )))
                .map(StoreDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 가게 정보 조회
     * @param storeId
     * @return Store
     */
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 특정 가게 정보 수정
     * @param storeId
     * @param updateRequest
     * @return StoreDto.Response
     */
    public StoreDto.Response updateStore(Long storeId, UpdateStoreDto updateRequest) {
        Store store = getStoreById(storeId);

        if (updateRequest.getStoreName() != null) {
            store.setStoreName(updateRequest.getStoreName());
        }

        if (updateRequest.getStoreAddress() != null) {
            store.setStoreAddress(updateRequest.getStoreAddress());
        }

        if (updateRequest.getCapacityPerson() != null) {
            store.setCapacityPerson(updateRequest.getCapacityPerson());
        }

        return StoreDto.Response.fromEntity(
                storeRepository.save(store)
        );
    }

    /**
     * 특정 가게 정보 삭제
     * @param storeId
     */
    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }

    /**
     * 리뷰 평점 갱신
     * @param storeId
     * @param rating
     */
    public void updateStoreRating(Long storeId, Double rating) {
        Store store = getStoreById(storeId);
        store.setRating(rating);
        storeRepository.save(store);
    }

    /**
     * 사용자의 위치와 가게 까지의 거리 측정
     * @param userLat
     * @param userLng
     * @param storeLat
     * @param storeLng
     * @return double
     */
    public double calculateDistance(double userLat, double userLng, double storeLat, double storeLng) {
        final int EARTH_RADIUS = 6371; // 지구 반지름 (킬로미터)

        double latDistance = Math.toRadians(storeLat - userLat);
        double lngDistance = Math.toRadians(storeLng - userLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(storeLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // 계산된 거리 반환 (킬로미터)
    }
}
