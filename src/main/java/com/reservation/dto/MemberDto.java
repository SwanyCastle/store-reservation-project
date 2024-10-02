package com.reservation.dto;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .role(member.getRole())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
