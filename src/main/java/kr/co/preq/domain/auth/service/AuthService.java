package kr.co.preq.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.preq.domain.auth.dto.AuthRequestDto;
import kr.co.preq.domain.auth.dto.AuthResponseDto;
import kr.co.preq.domain.member.dto.MemberRequestDto;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.global.common.util.SecurityUtil;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.jwt.TokenDto;
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
		TokenDto tokenDto = tokenProvider.generateTokenDto(member.getEmail());
		return AuthResponseDto.from(tokenDto, member);
	}

	@Transactional(readOnly = true)
	public Member findMember() {
		return memberRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
			.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}

}
