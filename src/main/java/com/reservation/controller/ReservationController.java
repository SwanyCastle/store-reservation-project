package com.reservation.controller;

import com.reservation.dto.reservation.ReservationDto;
import com.reservation.dto.reservation.UpdateReservationDto;
import com.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 등록 신청
     * @param request
     * @return ReservationDto.Response
     */
    @PostMapping
    public ReservationDto.Response reservationRegister(ReservationDto.Request request) {
        return reservationService.createReservation(request);
    }

    /**
     * 특정 가게에 대한 예약 목록 조회
     * @param storeId
     * @return List<ReservationDto.Response>
     */
    @GetMapping("/{storeId}")
    @PreAuthorize("hasRole('OWNER')")
    public List<ReservationDto.Response> reservationList(
            @PathVariable @Valid Long storeId
    ) {
        return reservationService.getReservationsByStoreId(storeId);
    }

    /**
     * 특정 예약 정보 조회
     * @param reservationId
     * @return ReservationDto.Response
     */
    @GetMapping("/{reservationId}")
    public ReservationDto.Response reservationDetails(
            @PathVariable @Valid Long reservationId
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
            @PathVariable @Valid Long reservationId
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
            @PathVariable @Valid Long reservationId
    ) {
        return reservationService.rejectReservation(reservationId);
    }

    /**
     * 도착 확인
     * @param reservationId
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}/visit")
    @PreAuthorize("hasRole('USER')")
    public ReservationDto.Response reservationVisit(
            @PathVariable @Valid Long reservationId
    ) {
        return reservationService.visitReservation(reservationId);
    }

    /**
     * 특정 예약 정보 수정
     * @param reservationId
     * @param updateRequest
     * @return ReservationDto.Response
     */
    @PatchMapping("/{reservationId}")
    public ReservationDto.Response updateReservation(
            @PathVariable @Valid Long reservationId,
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
            @PathVariable @Valid Long reservationId
    ) {
        reservationService.reservationDelete(reservationId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }

}
