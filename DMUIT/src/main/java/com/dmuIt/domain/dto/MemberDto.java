package com.dmuIt.domain.dto;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

public class MemberDto {
    @Getter
    @Setter
    public static class SignIn {
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String phone;
        private String major;
        private Integer grade;
        private String _class;
        private Set<String> skill;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private String name;
        private String nickname;
        private String phone;
        private String major;
        private Integer grade;
        private String _class;
        private Set<String> skill;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private String name;
        private String nickname;
        private String email;
        private String phone;
        private String major;
        private Integer grade;
        private String _class;
        private Set<String> skill;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private Long accessTokenExpirationTime;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
        private Long memberId;
    }
}
