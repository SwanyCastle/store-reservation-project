package com.reservation.dto.member;

import com.reservation.type.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SignUpDto {

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

        @NotNull
        private Role role;

        @NotNull
        @NotBlank
        private String phoneNumber;
    }

}
