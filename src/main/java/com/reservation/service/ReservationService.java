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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        Store store = storeService.getStoreById(request.getStoreId());

        checkReservationTime(request.getReservationDate());
        checkExistsReservation(member, store, request.getReservationDate());

        if (!possibleReservation(request.getVisitorNum(), store)) {
           throw new ReservationException(ErrorCode.RESERVATION_CAPACITY_OVER);
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
     * 예약 시간 검증
     * 예약 시간 < 현재시간 + 10분 -> ReservationException
     * @param reservationDate
     */
    private void checkReservationTime(LocalDateTime reservationDate) {
        LocalDateTime now = LocalDateTime.now();

        if (reservationDate.toLocalDate().equals(now.toLocalDate())) {
            if (reservationDate.isBefore(now.plusMinutes(10))) {
                throw new ReservationException(ErrorCode.RESERVATION_LATE_TIME);
            }
        }
    }

    /**
     * 예약 중복 체크
     * @param member
     * @param store
     * @param dateTime
     */
    public void checkExistsReservation(
            Member member, Store store, LocalDateTime dateTime
    ) {
        Optional<Reservation> reservation =
                reservationRepository.findByMemberAndStoreAndReservationDate(
                        member, store, dateTime
                );

        if (reservation.isPresent()) {
            throw new ReservationException(ErrorCode.RESERVATION_ALREADY_EXISTS);
        }
    }

    /**
     * 가게의 수용인원을 기준으로 예약가능 불가능 판단
     * @param requestVisitorNum
     * @param store
     * @return boolean
     */
    public boolean possibleReservation(Integer requestVisitorNum, Store store) {
        Integer sumVisitorNum = reservationRepository.sumVisitorNumByStore(store);

        log.info("sumVisitorNum = {}", sumVisitorNum);

        sumVisitorNum += requestVisitorNum;

        log.info("sumVisitorNum = {}", sumVisitorNum);

        return sumVisitorNum <= store.getCapacityPerson();
    }

    /**
     * 특정 가게에 대한 예약 목록 조회
     * 날짜 정보가 있다면 날짜별로 조회
     * @param storeId
     * @param localDate
     * @return List<ReservationDto.Response>
     */
    public List<ReservationDto.Response> getReservationsByStoreId(Long storeId, LocalDate localDate) {
        Store store = storeService.getStoreById(storeId);

        if (localDate != null) {
            List<Reservation> reservationList =
                    reservationRepository.findReservationsByStoreAndDate(
                            store, localDate
                    );

            return reservationList.stream()
                    .map(ReservationDto.Response::fromEntity)
                    .collect(Collectors.toList());
        }

        List<Reservation> reservationList =
                reservationRepository.findReservationsByStore(store);

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
    public ReservationDto.Response visitReservation(Long reservationId, Long memberId) {
        Reservation reservation = getReservationById(reservationId);

        checkMember(memberId, reservation);
        checkSameDate(reservation);
        checkTimeOver(reservation);

        reservation.setStatus(ReservationStatus.CONFIRMATION);
        reservation.setVisited(true);

        return ReservationDto.Response.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /**
     * 예약자와 방문자가 동일하지 확인
     * @param memberId
     * @param reservation
     */
    private void checkMember(Long memberId, Reservation reservation) {
        Member member = memberService.getMemberById(memberId);

        if (!reservation.getMember().getId().equals(member.getId())) {
            throw new ReservationException(ErrorCode.RESERVATION_MEMBER_UNMATCHED);
        }
    }

    /**
     * 예약 날짜와 방문일자가 같은지 확인
     * @param reservation
     */
    private void checkSameDate(Reservation reservation) {
        LocalDate nowDate = LocalDateTime.now().toLocalDate();
        LocalDate reservationDate = reservation.getReservationDate().toLocalDate();

        if (!nowDate.equals(reservationDate)) {
            throw new ReservationException(ErrorCode.RESERVATION_DATE_UNMATCHED);
        }
    }

    /**
     * 예약 날짜 10분 전에 도착했는지 확인
     * @param reservation
     */
    private void checkTimeOver(Reservation reservation) {
        LocalDateTime visitLimitTime = reservation.getReservationDate().minusMinutes(10);

        if (LocalDateTime.now().isAfter(visitLimitTime)) {
            reservation.setStatus(ReservationStatus.REJECTION);
            throw new ReservationException(ErrorCode.RESERVATION_VISIT_TIME_OVER);
        }
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

        if (updateRequest.getVisitorNum() != null) {
            if (!possibleReservation(updateRequest.getVisitorNum(), reservation.getStore())) {
                throw new ReservationException(ErrorCode.RESERVATION_CAPACITY_OVER);
            }
            reservation.setVisitorNum(updateRequest.getVisitorNum());
        }

        if (updateRequest.getReservationDate() != null) {
            checkReservationTime(updateRequest.getReservationDate());
            reservation.setReservationDate(updateRequest.getReservationDate());
        }

        reservation.setStatus(ReservationStatus.WAITING);

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

    /**
     * 가게에 방문 했는지 확인
     * @param member
     * @param store
     * @return boolean
     */
    public boolean checkReservationVisited(Member member, Store store) {
        Optional<Reservation> reservationOptional =
                reservationRepository.findByMemberAndStore(member, store);
        Reservation reservation = reservationOptional.get();

        return reservation.isVisited() ||
                !reservation.getReservationDate().isBefore(LocalDateTime.now());
    }
}
