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

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public StoreDto.Response storeRegister(
            @RequestBody @Valid StoreDto.Request request
    ) {
        return storeService.createStore(request);
    }

    @GetMapping
    public List<StoreDto.Response> storeList() {
        return storeService.getStores();
    }

    @GetMapping("/{storeId}")
    public StoreDto.Response storeDetails(@PathVariable @Valid Long storeId) {
        return StoreDto.Response.fromEntity(
                storeService.getStoreById(storeId)
        );
    }

    @PatchMapping("/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public StoreDto.Response updateStore(
            @PathVariable @Valid Long storeId,
            @RequestBody @Valid UpdateStoreDto updateRequest
    ) {
        return StoreDto.Response.fromEntity(
                storeService.updateStore(storeId, updateRequest)
        );
    }

    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deleteStore(@PathVariable @Valid Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }
}
