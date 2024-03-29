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

@Component
@RequiredArgsConstructor
public class NaverOauth {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final RandomPassword randomPassword;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    public ResponseEntity<String> requestUserInfo(String accessToken) {
        String NAVER_USERINFO_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset-utf-8");

        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(NAVER_USERINFO_REQUEST_URL, HttpMethod.POST, request, String.class);
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

        String user_id = userInfo.get("response").get("email").asText();
        String name = userInfo.get("response").get("name").asText();

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
