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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberService memberService;

    /**
     * 가게 정보 등록
     * @param request
     * @return StoreDto.Response
     */
    public StoreDto.Response createStore(StoreDto.Request request) {
        Member member = memberService.getMemberById(request.getMemberId());

        return StoreDto.Response.fromEntity(
                storeRepository.save(
                        Store.builder()
                                .storeName(request.getStoreName())
                                .storeAddress(request.getStoreAddress())
                                .capacityPerson(request.getCapacityPerson())
                                .member(member)
                                .build()
                )
        );
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

        if (!updateRequest.getStoreName().isEmpty()) {
            store.setStoreName(updateRequest.getStoreName());
        }

        if (!updateRequest.getStoreAddress().isEmpty()) {
            store.setStoreAddress(updateRequest.getStoreAddress());
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
}
