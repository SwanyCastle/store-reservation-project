package com.reservation.service;

import com.reservation.domain.Member;
import com.reservation.dto.member.MemberDto;
import com.reservation.dto.member.SignInDto;
import com.reservation.dto.member.UpdateMemberDto;
import com.reservation.exception.MemberException;
import com.reservation.repository.MemberRepository;
import com.reservation.type.ErrorCode;
import com.reservation.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public MemberDto createMember(String username, String password, Role role) {
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

    /**
     * 유저 정보 존재 확인
     * @param username
     * @return UserDetails
     */
    public UserDetails getMemberByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. " + username));
    }

    /**
     * 유저 이름, 비밀번호 확인
     * @param singInMember
     * @return Member
     */
    public Member authenticate(SignInDto.Request singInMember) {
        Member member = (Member) getMemberByUsername(singInMember.getUsername());

        if (!passwordEncoder.matches(singInMember.getPassword(), member.getPassword())) {
            throw new MemberException(ErrorCode.PASSWORD_UNMATCHED);
        }

        return member;
    }

    /**
     * ID 로 유저 정보 조회
     * @param memberId
     * @return Member
     */
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 특정 유저 정보 수정
     * @param memberId
     * @param updateRequest
     * @return MemberDto
     */
    public MemberDto updateMember(Long memberId, UpdateMemberDto updateRequest) {
        Member member = getMemberById(memberId);

        if (updateRequest.getUsername() != null) {
            member.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getPassword() != null) {
            member.setPassword(getEncodePassword(updateRequest.getPassword()));
        }

        return MemberDto.fromEntity(
                memberRepository.save(member)
        );
    }

    /**
     * 특정 유저 정보 삭제
     * @param memberId
     */
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
