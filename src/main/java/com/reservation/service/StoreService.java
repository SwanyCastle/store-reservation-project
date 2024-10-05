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

    public StoreDto.Response createStore(StoreDto.Request request) {
        Member member = memberService.findMember(request.getMemberId());

        return StoreDto.Response.fromEntity(
                storeRepository.save(
                        Store.builder()
                                .storeName(request.getStoreName())
                                .storeAddress(request.getStoreAddress())
                                .member(member)
                                .build()
                )
        );
    }

    public List<StoreDto.Response> getStores() {
        List<Store> storeList = storeRepository.findAll();

        return storeList.stream()
                .map(StoreDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }

    public Store updateStore(Long storeId, UpdateStoreDto updateRequest) {
        Store store = getStoreById(storeId);

        if (!updateRequest.getStoreName().isEmpty()) {
            store.setStoreName(updateRequest.getStoreName());
        }

        if (!updateRequest.getStoreAddress().isEmpty()) {
            store.setStoreAddress(updateRequest.getStoreAddress());
        }

        return storeRepository.save(store);
    }

    public void deleteStore(Long storeId) {
        Store store = getStoreById(storeId);
        storeRepository.delete(store);
    }
}
