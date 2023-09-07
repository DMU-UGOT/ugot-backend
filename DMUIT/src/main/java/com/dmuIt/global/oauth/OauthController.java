package com.dmuIt.global.oauth;

import com.dmuIt.domain.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OauthController {
    private final ApiResponseDto response;
    private final GoogleOauth googleOauth;
    private final NaverOauth naverOauth;

    @PostMapping("/auth/google")
    public ResponseEntity googleLogin(@RequestBody AccessTokenDto accessToken) throws IOException {
        GetSocialOAuthRes getSocialOAuthRes = googleOauth.oAuthLogin(accessToken.getAccessToken());

        return response.success(getSocialOAuthRes);
    }

    @PostMapping("/auth/naver")
    public ResponseEntity naverLogin(@RequestBody AccessTokenDto accessToken) throws IOException {
        GetSocialOAuthRes getSocialOAuthRes = naverOauth.oAuthLogin(accessToken.getAccessToken());

        return response.success(getSocialOAuthRes);
    }
}
