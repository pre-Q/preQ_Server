package kr.co.preq.domain.preq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.preq.domain.preq.dto.SessionDto;
import kr.co.preq.domain.preq.dto.Message;
import kr.co.preq.domain.preq.dto.request.OpenAIRequestDto;
import kr.co.preq.domain.preq.dto.response.OpenAIResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {

	@Value("${openai.api.key}")
	private String API_KEY;

	@Value("${openai.model}")
	private String MODEL;

	@Value("${openai.api.url}")
	private String URL;

	@Value("${openai.api.command}")
	private String COMMAND;

	private String AUTHORIZATION = "Authorization";
	private String BEARER = "Bearer ";
	private Integer MAX_TOKEN = 300;
	private Double TEMPERATURE = 0.0;
	private Double TOP_P = 1.0;
	private Integer N = 3;
	private String MEDIA_TYPE = "application/json; charset=UTF-8";
	RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	public List<String> generateQuestions(String question, String answer, List<SessionDto> sessionDtoList) {
		List<Message> sessions = new ArrayList<>();

		for (int i = 0; i < sessionDtoList.size() - 1; i++) {
			sessions.add(new Message("user", makeUserPrompt(sessionDtoList.get(i).getQuestion(),
				sessionDtoList.get(i).getAnswer())));
			sessions.add(new Message("assistant", sessionDtoList.get(i+1).getQuestion()));
			System.out.println("session--------");
			System.out.println(makeUserPrompt(sessionDtoList.get(i).getQuestion(),
				sessionDtoList.get(i).getAnswer()));
			System.out.println(sessionDtoList.get(i+1).getQuestion());
		}

		String prompt = makeUserPrompt(question, answer);
		System.out.println("new---------");
		System.out.println(prompt);

		OpenAIResponseDto response = this.getResponse(
			this.buildHttpEntity(
				new OpenAIRequestDto(
					MODEL,
					N,
					COMMAND,
					sessions,
					prompt
				)
			)
		);

		return response.getChoices().stream().map(choice -> {
			return choice.getMessage().getContent();
		}).collect(Collectors.toList());
	}

	private HttpEntity<OpenAIRequestDto> buildHttpEntity(OpenAIRequestDto requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MEDIA_TYPE));
		headers.add(AUTHORIZATION, BEARER + API_KEY);
		return new HttpEntity<>(requestDto, headers);
	}

	private OpenAIResponseDto getResponse(HttpEntity<OpenAIRequestDto> chatGptRequestDtoHttpEntity) {
		ResponseEntity<OpenAIResponseDto> responseEntity = restTemplate.postForEntity(
			URL,
			chatGptRequestDtoHttpEntity,
			OpenAIResponseDto.class);

		return responseEntity.getBody();
	}

	private String makeUserPrompt(String question, String answer) {
		return "질문: " + question + "\n답변: " + answer;
	}
}
