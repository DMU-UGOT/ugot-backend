package com.dmuIt.global.oauth;

import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.domain.service.MemberService;
import com.dmuIt.global.auth.jwt.JwtTokenProvider;
import com.dmuIt.global.utils.RandomPassword;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoOauth {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RandomPassword randomPassword;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> requestUserInfo(String accessToken) {
        String KAKAO_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
        System.out.println("response.getBody() = " + response.getBody());
        return response;
    }

    public JsonNode getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(userInfoRes.getBody());
        System.out.println(jsonNode.get("response"));
        return jsonNode;
    }

    public GetSocialOAuthRes oAuthLogin(String accessToken) throws IOException {

        ResponseEntity<String> userInfoResponse = requestUserInfo(accessToken);
        JsonNode userInfo = getUserInfo(userInfoResponse);

        String user_id = userInfo.get("kakao_account").get("email").asText();
        String name = userInfo.get("kakao_account").get("profile").get("nickname").asText();

        long user_num = memberService.getUserNum(user_id);

        if (user_num != 0) {
            Member member = memberService.findVerifiedMemberByEmail(user_id);
            String password = randomPassword.getRandomPassword(10);
            member.setPassword(passwordEncoder.encode(password));
            memberRepository.save(member);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_id, password);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            MemberDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            tokenInfo.setMemberId(user_num);

            return new GetSocialOAuthRes(tokenInfo, accessToken, user_id, name, password);
        }
        else {
            return new GetSocialOAuthRes(null, null, user_id, name, randomPassword.getRandomPassword(10));
        }
    }
}
