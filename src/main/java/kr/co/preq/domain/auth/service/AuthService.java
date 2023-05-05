package kr.co.preq.domain.auth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.preq.domain.auth.dto.AuthRequestDto;
import kr.co.preq.domain.auth.dto.AuthResponseDto;
import kr.co.preq.domain.member.dto.MemberRequestDto;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.jwt.TokenDto;
import kr.co.preq.global.common.util.jwt.TokenProvider;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final TokenProvider tokenProvider;
	// private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Transactional
	public AuthResponseDto login(AuthRequestDto requestDto) {

		MemberRequestDto memberRequestDto = new MemberRequestDto();
		try {
			if ("kakao".equals(requestDto.getType())) {
				memberRequestDto = kakaoLogin(requestDto.getAccessToken());
			}
			else if ("google".equals(requestDto.getType())) {
				memberRequestDto = googleLogin(requestDto.getAccessToken());
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
		//UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), "");
		//Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		TokenDto tokenDto = tokenProvider.generateTokenDto(member.getEmail(), member.getName());

		return AuthResponseDto.from(tokenDto, member);
	}

	private MemberRequestDto kakaoLogin(String kakaoToken) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + kakaoToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("property_keys", "[\"kakao_account.name\", \"kakao_account.email\"]");

		HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			kakaoUserInfoRequest,
			String.class
		);

		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		System.out.println(jsonNode);

		// String name = jsonNode.get("kakao_account").get("name").asText();
		String name = "이효원";
		String email = jsonNode.get("kakao_account").get("email").asText();

		return new MemberRequestDto(name, email);
	}

	private MemberRequestDto googleLogin(String googleToken) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + googleToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
			"https://www.googleapis.com/oauth2/v1/userinfo",
			HttpMethod.GET,
			googleUserInfoRequest,
			String.class
		);

		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);

		String name = jsonNode.get("name").asText();
		String email = jsonNode.get("email").asText();

		return new MemberRequestDto(name, email);

	}

	@Transactional(readOnly = true)
	public Member findMember() {
		return memberRepository.findByEmail("preq@gmail.com")		//SecurityUtil.getCurrentUserEmail()
			.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}

}
