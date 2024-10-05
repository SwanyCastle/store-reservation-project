package com.reservation.dto.review;

import com.reservation.domain.Review;
import com.reservation.dto.member.MemberDto;
import com.reservation.dto.store.StoreDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class ReviewDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull
        @NotBlank
        private Long memberId;

        @NotNull
        @NotBlank
        private Long storeId;

        @NotNull
        @NotBlank
        private String content;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long reviewId;
        private MemberDto member;
        private StoreDto.Response store;
        private String content;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static ReviewDto.Response fromEntity(Review review) {
            return Response.builder()
                    .reviewId(review.getId())
                    .member(MemberDto.fromEntity(review.getMember()))
                    .store(StoreDto.Response.fromEntity(review.getStore()))
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();
        }
    }

}
