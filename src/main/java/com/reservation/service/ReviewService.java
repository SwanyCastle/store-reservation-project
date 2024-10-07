package com.reservation.service;

import com.reservation.domain.Member;
import com.reservation.domain.Review;
import com.reservation.domain.Store;
import com.reservation.dto.review.ReviewDto;
import com.reservation.dto.review.UpdateReviewDto;
import com.reservation.exception.ReviewException;
import com.reservation.repository.ReviewRepository;
import com.reservation.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberService memberService;
    private final StoreService storeService;
    private final ReservationService reservationService;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 등록
     * 방문한 고객인지 확인 및 이미 리뷰가 존재하는지 확인
     * @param request
     * @return ReviewDto.Response
     */
    public ReviewDto.Response createReview(ReviewDto.Request request) {
        Member member = memberService.getMemberById(request.getMemberId());
        Store store = storeService.getStoreById(request.getStoreId());

        if (reservationService.checkReservationVisited(member, store)) {
            throw new ReviewException(ErrorCode.REVIEW_NO_AUTHORIZATION);
        }
        checkExistsReview(member, store);

        storeService.updateStoreRating(
                request.getStoreId(), getRatingAverage(request.getStoreId())
        );

        return ReviewDto.Response.fromEntity(
                reviewRepository.save(
                        Review.builder()
                                .member(member)
                                .store(store)
                                .content(request.getContent())
                                .rating(request.getRating())
                                .build()
                )
        );
    }

    /**
     * 리뷰 중복 체크
     * @param member
     * @param store
     */
    public void checkExistsReview(Member member, Store store) {
        Optional<Review> review =
                reviewRepository.findByMemberAndStore(member, store);

        if (review.isPresent()) {
            throw new ReviewException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

    }

    /**
     * 특정 가게에 대한 리뷰 목록 조회
     * @param storeId
     * @return List<ReviewDto.Response>
     */
    public List<ReviewDto.Response> getReviewsByStoreId(Long storeId) {
        Store store = storeService.getStoreById(storeId);
        List<Review> reviewListByStore = reviewRepository.findAllByStore(store);

        return reviewListByStore.stream()
                .map(ReviewDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 유저가 작성한 리뷰 목록 조회
     * @param memberid
     * @return List<ReviewDto.Response>
     */
    public List<ReviewDto.Response> getReviewsByMemberId(Long memberid) {
        Member member = memberService.getMemberById(memberid);
        List<Review> reviewListByMember = reviewRepository.findAllByMember(member);

        return reviewListByMember.stream()
                .map(ReviewDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 리뷰 정보 조회
     * @param reviewId
     * @return Review
     */
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND));
    }

    /**
     * 특정 리뷰 정보 수정
     * @param updateRequest
     * @return ReviewDto.Response
     */
    public ReviewDto.Response updateReview(Long reviewId, UpdateReviewDto updateRequest) {
        Review review = getReviewById(reviewId);

        if (reservationService.checkReservationVisited(review.getMember(), review.getStore())) {
            throw new ReviewException(ErrorCode.REVIEW_NO_AUTHORIZATION);
        }

        if (updateRequest.getContent() != null) {
            review.setContent(updateRequest.getContent());
        }

        if (updateRequest.getRating() != null) {
            review.setRating(updateRequest.getRating());
            storeService.updateStoreRating(
                    review.getStore().getId(),
                    getRatingAverage(review.getStore().getId())
            );
        }

        return ReviewDto.Response.fromEntity(
                reviewRepository.save(review)
        );
    }

    /**
     * 특정 리뷰 삭제
     * @param reviewId
     */
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /**
     * 특정 가게에 대한 리뷰 평균값 조회
     * @param storeId
     * @return
     */
    public Double getRatingAverage(Long storeId) {
        Store store = storeService.getStoreById(storeId);
        Double avgRatingByStore = reviewRepository.avgRatingByStore(store);
        return avgRatingByStore != null ? avgRatingByStore : 0.0;
    }

}
