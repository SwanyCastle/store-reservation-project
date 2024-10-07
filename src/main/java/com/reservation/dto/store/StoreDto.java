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

        private String description;

        @NotNull
        private Long memberId;

        @NotNull
        private Integer capacityPerson;

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
        private String description;
        private MemberDto member;
        private Double rating;
        private Double latitude;
        private Double longitude;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static StoreDto.Response fromEntity(Store store) {
            return Response.builder()
                    .shopId(store.getId())
                    .storeName(store.getStoreName())
                    .storeAddress(store.getStoreAddress())
                    .description(store.getDescription())
                    .member(MemberDto.fromEntity(store.getMember()))
                    .rating(Double.parseDouble(String.format("%.1f", store.getRating())))
                    .latitude(store.getLatitude())
                    .longitude(store.getLongitude())
                    .createdAt(store.getCreatedAt())
                    .updatedAt(store.getUpdatedAt())
                    .build();
        }

    }
}
