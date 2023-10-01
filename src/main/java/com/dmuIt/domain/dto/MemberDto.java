package com.dmuIt.domain.dto;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
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
        private String major;
        private Integer grade;
        private String _class;
        private List<String> skill;
        private String gitHubLink;
        private String personalBlogLink;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private String name;
        private String nickname;
        private String major;
        private Integer grade;
        private String _class;
        private List<String> skill;
        private String gitHubLink;
        private String personalBlogLink;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private String name;
        private String nickname;
        private String email;
        private String major;
        private Integer grade;
        private String _class;
        private List<String> skill;
        private String gitHubLink;
        private String personalBlogLink;
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
