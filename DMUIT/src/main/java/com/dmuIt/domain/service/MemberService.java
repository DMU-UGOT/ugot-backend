package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.ApiResponseDto;
import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.FavoriteRepository;

import com.dmuIt.domain.entity.TeamBookmark;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.global.auth.jwt.JwtTokenProvider;
import com.dmuIt.global.auth.lib.Helper;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import com.dmuIt.global.redis.entity.RefreshToken;
import com.dmuIt.global.redis.repository.RefreshTokenRedisRepository;
import com.dmuIt.global.utils.CustomAuthorityUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final ApiResponseDto response;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<?> signIn(HttpServletRequest request, MemberDto.SignIn signIn) {
        UsernamePasswordAuthenticationToken authenticationToken = signIn.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        MemberDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        Member verifiedMember = findVerifiedMemberByEmail(signIn.getEmail());
        tokenInfo.setMemberId(verifiedMember.getMemberId());

        refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(authentication.getName())
                .ip(Helper.getClientIp(request))
                .authorities(authentication.getAuthorities())
                .refreshToken(tokenInfo.getRefreshToken())
                .build());

        return response.success(tokenInfo);
    }

    public ResponseEntity<?> reissue(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            if (jwtTokenProvider.isRefreshToken(token)) {
                RefreshToken refreshToken = refreshTokenRedisRepository.findByRefreshToken(token);
                if (refreshToken != null) {
                    String currentIpAddress = Helper.getClientIp(request);
                    if (refreshToken.getIp().equals(currentIpAddress)) {
                        MemberDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(refreshToken.getId(), refreshToken.getAuthorities());

                        refreshTokenRedisRepository.save(RefreshToken.builder()
                                .id(refreshToken.getId())
                                .ip(currentIpAddress)
                                .authorities(refreshToken.getAuthorities())
                                .refreshToken(tokenInfo.getRefreshToken())
                                .build());

                        return response.success(tokenInfo);
                    }
                }
            }
        }
        return response.fail("토큰 갱신에 실패했습니다.");
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsNickname(member.getNickname());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMember(HttpServletRequest request, Member member) {
        Member currentMember = verifiedCurrentMember(request);
        if (currentMember.getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Member findMember = findVerifiedMember(member.getMemberId());
        Optional.ofNullable(member.getName())
                .ifPresent(findMember::setName);
        Optional.ofNullable(member.getNickname())
                .ifPresent(findMember::setNickname);
        Optional.ofNullable(member.getMajor())
                .ifPresent(findMember::setMajor);
        Optional.ofNullable(member.getGrade())
                .ifPresent(findMember::setGrade);
        Optional.ofNullable(member.get_class())
                .ifPresent(findMember::set_class);
        Optional.ofNullable(member.getSkill())
                .ifPresent(findMember::setSkill);
        Optional.ofNullable(member.getGitHubLink())
                .ifPresent(findMember::setGitHubLink);
        Optional.ofNullable(member.getPersonalBlogLink())
                .ifPresent(findMember::setPersonalBlogLink);
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(HttpServletRequest request, long memberId) {
        Member member = verifiedCurrentMember(request);
        if (memberId != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Member verifiedMember = findVerifiedMember(memberId);
        memberRepository.delete(verifiedMember);
    }

    public Member verifiedCurrentMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        Claims claims = jwtTokenProvider.parseClaims(accessToken);
        String email = claims.getSubject();
        return findVerifiedMemberByEmail(email);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    public Member findVerifiedMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        Member findMember = member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    public void verifyExistsNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }

    public ResponseEntity<?> checkNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            return new ResponseEntity<>("존재하는 닉네임입니다.", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    public long getUserNum(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return member.get().getMemberId();
        }
        else return 0;
    }



}
