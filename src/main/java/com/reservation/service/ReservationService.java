package com.reservation.service;

import com.reservation.domain.Member;
import com.reservation.domain.Reservation;
import com.reservation.domain.Store;
import com.reservation.dto.reservation.ReservationDto;
import com.reservation.dto.reservation.UpdateReservationDto;
import com.reservation.exception.ReservationException;
import com.reservation.repository.ReservationRepository;
import com.reservation.type.ErrorCode;
import com.reservation.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final MemberService memberService;
    private final StoreService storeService;
    private final ReservationRepository reservationRepository;

    /**
     * 예약 등록 신청
     * @param request
     * @return ReservationDto.Response
     */
    public ReservationDto.Response createReservation(ReservationDto.Request request) {

        Member member = memberService.getMemberById(request.getMemberId());
        Store store = storeService.getStoreById(request.getShopId());

        if (!possibleReservation(request, store)) {
           throw new ReservationException(ErrorCode.RESERVATION_IMPOSSIBLE);
        }

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(
                        Reservation.builder()
                                .member(member)
                                .store(store)
                                .visitorNum(request.getVisitorNum())
                                .isVisited(false)
                                .status(ReservationStatus.WAITING)
                                .reservationDate(request.getReservationDate())
                                .build()
                )
        );
    }

    /**
     * 가게의 수용인원을 기준으로 예약가능 불가능 판단
     * @param request
     * @param store
     * @return boolean
     */
    public boolean possibleReservation(ReservationDto.Request request, Store store) {
        Integer sumVisitorNum = reservationRepository.sumVisitorNumByStore(store);

        sumVisitorNum += request.getVisitorNum();

        return sumVisitorNum <= store.getCapacityPerson();
    }

    /**
     * 특정 가게에 대한 예약 목록 조회
     * @param storeId
     * @return List<ReservationDto.Response>
     */
    public List<ReservationDto.Response> getReservationsByStoreId(Long storeId) {
        Store store = storeService.getStoreById(storeId);

        List<Reservation> reservationList = reservationRepository.findReservationsByStore(store);

        return reservationList.stream()
                .map(ReservationDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 예약 정보 조회
     * @param reservationId
     * @return Reservation
     */
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    /**
     * 예약 승인
     * 가게 측에서 예약 승인시
     * 예약 상태정보 업데이트
     * @param reservationId
     * @return ReservationDto.Response
     */
    public ReservationDto.Response confirmReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservation.setStatus(ReservationStatus.CONFIRMATION);

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /**
     * 예약 거절
     * 가게 측에서 예약 거절시
     * 예약 상태정보 업데이트
     * @param reservationId
     * @return ReservationDto.Response
     */
    public ReservationDto.Response rejectReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservation.setStatus(ReservationStatus.REJECTION);

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /**
     * 도착 확인
     * 도착 했을 당시 예약시간 10분 전인지 체크 후
     * 도착 정보 업데이트
     * @param reservationId
     * @return ReservationDto.Response
     */
    public ReservationDto.Response visitReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        LocalDateTime visitLimitTime = reservation.getReservationDate().minusMinutes(10);

        if (LocalDateTime.now().isAfter(visitLimitTime)) {
            reservation.setStatus(ReservationStatus.REJECTION);
            throw new ReservationException(ErrorCode.RESERVATION_VISIT_TIME_OVER);
        }

        reservation.setStatus(ReservationStatus.CONFIRMATION);
        reservation.setVisited(true);

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /**
     * 특정 예약 정보 수정
     * @param reservationId
     * @param updateRequest
     * @return ReservationDto.Response
     */
    public ReservationDto.Response reservationUpdate(
            Long reservationId, UpdateReservationDto updateRequest
    ) {
        Reservation reservation = getReservationById(reservationId);

        if (updateRequest.getStatus() != null) {
            reservation.setStatus(updateRequest.getStatus());
        }

        if (updateRequest.getVisitorNum() != null) {
            reservation.setVisitorNum(updateRequest.getVisitorNum());
        }

        if (updateRequest.isVisited() != reservation.isVisited()) {
            reservation.setVisited(updateRequest.isVisited());
        }

        if (updateRequest.getReservationDate() != null) {
            reservation.setReservationDate(updateRequest.getReservationDate());
        }

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /**
     * 특정 예약 정보 삭제
     * @param reservationId
     */
    public void reservationDelete(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservationRepository.delete(reservation);
    }
}
