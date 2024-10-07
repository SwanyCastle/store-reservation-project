package com.reservation.dto.member;

import com.reservation.domain.Member;
import com.reservation.type.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long memberId;
    private String username;
    private Role role;
    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .role(member.getRole())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
