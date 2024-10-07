package com.reservation.controller;

import com.reservation.domain.Member;
import com.reservation.dto.member.MemberDto;
import com.reservation.dto.member.SignInDto;
import com.reservation.dto.member.SignUpDto;
import com.reservation.dto.member.UpdateMemberDto;
import com.reservation.security.TokenProvider;
import com.reservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /**
     * 회원 가입
     * @param request
     * @return CreateMemberDTO.Response
     */
    @PostMapping("/register")
    public MemberDto memberRegister(
            @RequestBody @Valid SignUpDto.Request request
    ) {
        return memberService.createMember(request);
    }

    /**
     * 로그인
     * @param request
     * @return SignInDto.Response
     */
    @PostMapping("/login")
    public SignInDto.Response memberSignIn(
            @RequestBody @Valid SignInDto.Request request
    ) {
        Member authenticatedMember = memberService.authenticate(request);
        String token = tokenProvider.generateToken(
                authenticatedMember.getUsername(), authenticatedMember.getRole()
        );

        return SignInDto.Response.builder()
                .accessToken(token)
                .build();
    }

    /**
     * 특정 유저 정보 조회
     * @param userId
     * @return MemberDto
     */
    @GetMapping("/{userId}")
    public MemberDto findMember(@PathVariable Long userId) {
        return MemberDto.fromEntity(
                memberService.getMemberById(userId)
        );
    }

    /**
     * 특정 유저 정보 수정
     * @param userId
     * @param updateRequest
     * @return MemberDto
     */
    @PatchMapping("/{userId}")
    public MemberDto updateMember(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateMemberDto updateRequest
            ) {
        return memberService.updateMember(userId, updateRequest);
    }

    /**
     * 특정 유저 정보 삭제
     * @param userId
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long userId) {
        memberService.deleteMember(userId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }

}
