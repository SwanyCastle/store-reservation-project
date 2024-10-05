package com.reservation.dto.store;

import com.reservation.domain.Store;
import com.reservation.dto.member.MemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class StoreDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull
        @NotBlank
        private String storeName;

        @NotNull
        @NotBlank
        private String storeAddress;

        @NotNull
        @NotBlank
        private Long memberId;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long shopId;
        private String storeName;
        private String storeAddress;
        private MemberDto member;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static StoreDto.Response fromEntity(Store store) {
            return StoreDto.Response.builder()
                    .shopId(store.getId())
                    .storeName(store.getStoreName())
                    .storeAddress(store.getStoreAddress())
                    .member(MemberDto.fromEntity(store.getMember()))
                    .build();
        }

    }
}
