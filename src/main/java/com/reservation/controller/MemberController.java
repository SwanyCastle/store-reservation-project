package com.reservation.controller;

import com.reservation.dto.CreateMemberDTO;
import com.reservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  유저 생성, 수정, 삭제
 *  기능을 위한 Controller 처리
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입
     * @param request
     * @return CreateMemberDTO.Response
     */
    @PostMapping
    public CreateMemberDTO.Response memberRegister(
            @RequestBody @Valid CreateMemberDTO.Request request
    ) {
        return CreateMemberDTO.Response.fromMemberDto(
                memberService.registerMember(
                        request.getUsername(),
                        request.getPassword(),
                        request.getRole()
                )
        );
    }
}
