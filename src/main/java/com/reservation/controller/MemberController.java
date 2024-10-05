package com.reservation.controller;

import com.reservation.domain.Member;
import com.reservation.dto.member.MemberDto;
import com.reservation.dto.member.SignInDto;
import com.reservation.dto.member.SignUpDto;
import com.reservation.security.TokenProvider;
import com.reservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *  유저 생성, 수정, 삭제
 *  기능을 위한 Controller 처리
 */
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
        return memberService.createMember(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );
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
     * id 로 유저 정보 검색
     * @param userId
     * @return MemberDto
     */
    @GetMapping("/{userId}")
    public MemberDto findMember(@PathVariable @Valid Long userId) {
        return MemberDto.fromEntity(
                memberService.getMemberById(userId)
        );
    }
}
