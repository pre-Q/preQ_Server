package kr.co.preq.domain.auth.service;

import java.util.UUID;

import kr.co.preq.domain.auth.dto.request.AuthRequestDto;
import kr.co.preq.domain.auth.dto.response.AuthResponseDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.preq.domain.auth.dto.request.LogoutRequestDto;
import kr.co.preq.domain.auth.dto.request.TokenRequestDto;
import kr.co.preq.domain.member.dto.request.MemberRequestDto;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.global.common.util.RedisUtil;
import kr.co.preq.global.common.util.SecurityUtil;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.jwt.TokenDto;
import kr.co.preq.global.common.util.jwt.TokenInfo;
import kr.co.preq.global.common.util.jwt.TokenProvider;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService{
	private final KaKaoAuthService kaKaoAuthService;
	private final GoogleAuthService googleAuthService;
	private final MemberRepository memberRepository;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RedisUtil redisUtil;

	@Transactional
	public AuthResponseDto login(AuthRequestDto requestDto) {

		MemberRequestDto memberRequestDto = new MemberRequestDto();
		try {
			if ("kakao".equals(requestDto.getType())) {
				memberRequestDto = kaKaoAuthService.kakaoLogin(requestDto.getAccessToken());
			}
			else if ("google".equals(requestDto.getType())) {
				memberRequestDto = googleAuthService.googleLogin(requestDto.getAccessToken());
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// DB에 이미 있는지 확인
		Member member = memberRepository.findByEmail(memberRequestDto.getEmail()).orElse(null);

		// 없으면 회원가입
		if (member == null) {
			member = Member.builder()
				.name(memberRequestDto.getName())
				.email(memberRequestDto.getEmail())
				.build();
			memberRepository.save(member);
		}

		// 로그인
		UsernamePasswordAuthenticationToken authenticationToken = member.toAuthentication();
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		TokenDto tokenDto = getRedisTokenKey(authentication);
		return AuthResponseDto.from(tokenDto, member);
	}

	@Transactional
	public void logout(LogoutRequestDto logoutRequestDto) {
		String accessToken = logoutRequestDto.getAccessToken();
		if (!tokenProvider.validateToken(accessToken)) {
			throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
		}

		String refreshTokenKey = RedisUtil.PREFIX_REFRESH_TOKEN + logoutRequestDto.getRefreshToken();

		String refreshToken = redisUtil.getData(refreshTokenKey);
		if (refreshToken == null) {
			throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
		}
		redisUtil.deleteData(refreshTokenKey);

		redisUtil.setDataExpire(RedisUtil.PREFIX_LOGOUT + accessToken, "logout", TokenInfo.ACCESS_TOKEN_EXPIRE_TIME);
	}

	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) {
		String key = RedisUtil.PREFIX_REFRESH_TOKEN + tokenRequestDto.getRefreshToken();
		String refreshToken = redisUtil.getData(key);
		if (refreshToken == null) {
			throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
		}
		redisUtil.deleteData(key);

		Authentication authentication = tokenProvider.getAuthentication(refreshToken);
		return getRedisTokenKey(authentication);
	}

	@Transactional(readOnly = true)
	public Member findMember() {
		return memberRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
			.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	private TokenDto getRedisTokenKey(Authentication authentication) {
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

		String uuid = generateUUID();
		String refreshTokenKey = RedisUtil.PREFIX_REFRESH_TOKEN + uuid;
		while (redisUtil.getData(refreshTokenKey) != null) {
			uuid = generateUUID();
			refreshTokenKey = RedisUtil.PREFIX_REFRESH_TOKEN + uuid;
		}
		redisUtil.setDataExpire(refreshTokenKey, tokenDto.getRefreshToken(), TokenInfo.REFRESH_TOKEN_EXPIRE_TIME);

		tokenDto.setRefreshToken(uuid);
		return tokenDto;
	}

	private String generateUUID() {
		return UUID.randomUUID().toString().substring(0, 6);
	}
}
