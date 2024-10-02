package com.reservation.dto;

import com.reservation.type.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CreateMemberDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @NotBlank
        private String username;

        @NotNull
        @NotBlank
        private String password;

        private Role role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long memberId;
        private String username;
        private Role role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromMemberDto(MemberDto memberDto) {
            return Response.builder()
                    .memberId(memberDto.getMemberId())
                    .username(memberDto.getUsername())
                    .role(memberDto.getRole())
                    .createdAt(memberDto.getCreatedAt())
                    .updatedAt(memberDto.getUpdatedAt())
                    .build();
        }
    }
}
