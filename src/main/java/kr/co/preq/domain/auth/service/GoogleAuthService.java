package kr.co.preq.domain.auth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.preq.domain.member.dto.request.MemberRequestDto;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

	@Transactional
	public MemberRequestDto googleLogin(String googleToken) throws JsonProcessingException {
		try {
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
		} catch (HttpClientErrorException e) {
			throw new CustomException(ErrorCode.GOOGLE_UNAUTHORIZED_USER);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
