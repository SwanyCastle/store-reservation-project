package com.reservation.controller;

import com.reservation.dto.review.ReviewDto;
import com.reservation.dto.review.UpdateReviewDto;
import com.reservation.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewDto.Response reviewRegister(
            @RequestBody @Valid ReviewDto.Request request
    ) {
        return reviewService.createReview(request);
    }

    @GetMapping("/{storeId}")
    public List<ReviewDto.Response> reviewListByStoreId(
            @PathVariable @Valid Long storeId
    ) {
        return reviewService.getReviewsByStoreId(storeId);
    }

    @GetMapping("/{memberId}")
    public List<ReviewDto.Response> reviewListByMemberId(
            @PathVariable @Valid Long memberId
    ) {
        return reviewService.getReviewsByMemberId(memberId);
    }

    @GetMapping("/{reviewId}")
    public ReviewDto.Response reviewDetails(@PathVariable @Valid Long reviewId) {
        return ReviewDto.Response.fromEntity(
                reviewService.getReviewById(reviewId)
        );
    }

    @PatchMapping("/{reviewId}")
    public ReviewDto.Response updateReview(
            @PathVariable @Valid Long reviewId,
            @RequestBody@Valid UpdateReviewDto updateRequest
    ) {
        return reviewService.updateReview(reviewId, updateRequest);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable @Valid Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }
}
