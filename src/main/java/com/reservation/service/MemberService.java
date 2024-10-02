package com.reservation.service;

import com.reservation.domain.Member;
import com.reservation.dto.MemberDto;
import com.reservation.exception.MemberException;
import com.reservation.repository.MemberRepository;
import com.reservation.type.ErrorCode;
import com.reservation.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 유저 생성, 수정, 삭제
 * 기능을 위한 Service 처리
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     * 회원 가입 기능
     * @param username
     * @param password
     * @param role
     * @return
     */
    public MemberDto registerMember(String username, String password, Role role) {
        checkExistsMember(username);

        String encodePassword = getEncodePassword(password);

        return MemberDto.fromEntity(
                memberRepository.save(
                        Member.builder()
                                .username(username)
                                .password(encodePassword)
                                .role(role)
                                .build()
                )
        );
    }

    /**
     * 비밀번호 암호화
     * @param password
     * @return String
     */
    private String getEncodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 유저 정보 중복 체크
     * @param username
     */
    private void checkExistsMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            throw new MemberException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }
    }
}
