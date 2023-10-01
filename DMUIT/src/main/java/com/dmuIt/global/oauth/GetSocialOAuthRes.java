package com.dmuIt.global.oauth;

import com.dmuIt.domain.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {
    MemberDto.TokenInfo tokenInfo;
    private String accessToken;
    private String email;
    private String name;
    private String password;
}
