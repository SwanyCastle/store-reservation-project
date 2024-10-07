package com.reservation.controller;

import com.reservation.dto.reservation.ReservationDto;
import com.reservation.dto.reservation.UpdateReservationDto;
import com.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 등록 신청
     * @param request
     * @return ReservationDto.Response
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ReservationDto.Response reservationRegister(
            @RequestBody @Valid ReservationDto.Request request) {
        return reservationService.createReservation(request);
    }

    /**
     * 특정 가게에 대한 예약 목록 전체 조회
     * @param storeId
     * @return List<ReservationDto.Response>
     */
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public List<ReservationDto.Response> reservationList(
            @PathVariable Long storeId,
            @RequestParam(required = false) String date
    ) {
        LocalDate localDate = null;
        if (!date.isEmpty()) {
            localDate = LocalDate.parse(date);
        }
        return reservationService.getReservationsByStoreId(storeId, localDate);
    }

    /**
     * 특정 예약 정보 조회
     * @param reservationId
     * @return ReservationDto.Response
     */
    @GetMapping("/{reservationId}")
    public ReservationDto.Response reservationDetails(
            @PathVariable Long reservationId
    ) {
        return ReservationDto.Response.fromEntity(
                reservationService.getReservationById(reservationId)
        );
    }

    /**
     * 예약 승인
     * @param reservationId
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}/confirm")
    @PreAuthorize("hasRole('OWNER')")
    public ReservationDto.Response reservationConfirm(
            @PathVariable Long reservationId
    ) {
        return reservationService.confirmReservation(reservationId);
    }

    /**
     * 예약 거절
     * @param reservationId
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}/reject")
    @PreAuthorize("hasRole('OWNER')")
    public ReservationDto.Response reservationReject(
            @PathVariable Long reservationId
    ) {
        return reservationService.rejectReservation(reservationId);
    }

    /**
     * 도착 확인
     * @param reservationId
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}/visit/{memberId}")
    @PreAuthorize("hasRole('USER')")
    public ReservationDto.Response reservationVisit(
            @PathVariable Long reservationId,
            @PathVariable Long memberId
    ) {
        return reservationService.visitReservation(reservationId, memberId);
    }

    /**
     * 특정 예약 정보 수정
     * @param reservationId
     * @param updateRequest
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public ReservationDto.Response updateReservation(
            @PathVariable Long reservationId,
            @RequestBody @Valid UpdateReservationDto updateRequest
    ) {
        return reservationService.reservationUpdate(reservationId, updateRequest);
    }

    /**
     * 특정 예약 정보 삭제
     * @param reservationId
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.reservationDelete(reservationId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }

}
