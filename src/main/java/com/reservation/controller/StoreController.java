package com.reservation.controller;

import com.reservation.dto.store.StoreDto;
import com.reservation.dto.store.UpdateStoreDto;
import com.reservation.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 등록
     * @param request
     * @return StoreDto.Response
     */
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public StoreDto.Response storeRegister(
            @RequestBody @Valid StoreDto.Request request
    ) {
        return storeService.createStore(request);
    }

    /**
     * 전체 가게 목록 (가나다, 평점, 거리순 정렬)
     * @return List<StoreDto.Response>
     */
    @GetMapping
    public List<StoreDto.Response> storeList(
            @RequestParam(required = false, defaultValue = "name") String sortType,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLng
    ) {
        return switch (sortType) {
            case "distance" ->
                    storeService.getStoresSortedByDistance(userLat, userLng);
            case "rating" -> storeService.getStoresSortedByRating();
            default -> storeService.getStoresSortedByName();
        };
    }


    /**
     * 특정 가게 정보 조회
     * @param storeId
     * @return StoreDto.Response
     */
    @GetMapping("/{storeId}")
    public StoreDto.Response storeDetails(@PathVariable @Valid Long storeId) {
        return StoreDto.Response.fromEntity(
                storeService.getStoreById(storeId)
        );
    }

    /**
     * 특정 가게 정보 수정
     * @param storeId
     * @param updateRequest
     * @return StoreDto.Response
     */
    @PatchMapping("/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public StoreDto.Response updateStore(
            @PathVariable @Valid Long storeId,
            @RequestBody @Valid UpdateStoreDto updateRequest
    ) {
        return storeService.updateStore(storeId, updateRequest);
    }

    /**
     * 특정 가게 정보 삭제
     * @param storeId
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deleteStore(@PathVariable @Valid Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }
}
