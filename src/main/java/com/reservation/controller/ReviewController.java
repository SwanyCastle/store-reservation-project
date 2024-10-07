package com.reservation.controller;

import com.reservation.dto.review.ReviewDto;
import com.reservation.dto.review.UpdateReviewDto;
import com.reservation.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록
     * @param request
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ReviewDto.Response reviewRegister(
            @RequestBody @Valid ReviewDto.Request request
    ) {
        return reviewService.createReview(request);
    }

    /**
     * 특정 가게에 대한 리뷰 목록 조회
     * @param storeId
     * @return
     */
    @GetMapping("/store/{storeId}")
    public List<ReviewDto.Response> reviewListByStoreId(
            @PathVariable @Valid Long storeId
    ) {
        return reviewService.getReviewsByStoreId(storeId);
    }

    /**
     * 특정 유저가 작성한 리뷰 목록 조회
     * @param memberId
     * @return
     */
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('USER')")
    public List<ReviewDto.Response> reviewListByMemberId(
            @PathVariable @Valid Long memberId
    ) {
        return reviewService.getReviewsByMemberId(memberId);
    }

    /**
     * 특정 리뷰 조회
     * @param reviewId
     * @return
     */
    @GetMapping("/{reviewId}")
    public ReviewDto.Response reviewDetails(@PathVariable @Valid Long reviewId) {
        return ReviewDto.Response.fromEntity(
                reviewService.getReviewById(reviewId)
        );
    }

    /**
     * 특정 리뷰 수정
     * @param reviewId
     * @param updateRequest
     * @return
     */
    @PatchMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ReviewDto.Response updateReview(
            @PathVariable @Valid Long reviewId,
            @RequestBody@Valid UpdateReviewDto updateRequest
    ) {
        return reviewService.updateReview(reviewId, updateRequest);
    }

    /**
     * 특정 리뷰 삭제
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable @Valid Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }
}
