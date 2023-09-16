package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.preq.dto.request.KeywordAndSoftskillsRequestDto;
import kr.co.preq.domain.preq.dto.response.KeywordAndSoftskillsResponseDto;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.preq.dto.response.PreqAndKeywordResponseDto;
import kr.co.preq.domain.preq.dto.response.PreqMapper;
import kr.co.preq.domain.preq.entity.Preq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.applicationChild.repository.ApplicationChildRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import kr.co.preq.global.common.util.exception.BadRequestException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final ApplicationChildRepository applicationChildRepository;
	private final OpenAIService openAIService;
	private final PreqMapper preqMapper;

	@Value("${flask.url}") private String FLASK_URL;

	@Transactional
	public PreqAndKeywordResponseDto createPreqAndKeyword(Long applicationChildId) {

		ApplicationChild applicationChild = applicationChildRepository.findById(applicationChildId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.NO_ID));

		// generate preQuestions
		List<String> questions = openAIService.generateQuestions(applicationChild.getQuestion(), applicationChild.getAnswer());

		// manufacturing chatGPT response
		List<String> cutQuestions = new ArrayList<>();
		questions.forEach(q -> {
			if (q.contains(": ")) {
				String[] str = q.split(": ");
				cutQuestions.add(str[1]);
			} else {
				cutQuestions.add(q);
			}
		});

		// insert generated preQuestions into DB
		cutQuestions.forEach(q -> {
			Preq preq = Preq.builder()
				.question(q)
				.applicationChild(applicationChild)
				.build();
			preqRepository.save(preq);
		});

		// extract keywords
		KeywordAndSoftskillsResponseDto keywordAndSoftskillsInfo = sendRequestToFlask(KeywordAndSoftskillsRequestDto.builder()
			.application(applicationChild.getAnswer())
			.build());

		// update application child keywords and abilities
		applicationChild.updateKeywords(keywordAndSoftskillsInfo.getData().getKeywordTop5());
		applicationChild.updateAbilities(keywordAndSoftskillsInfo.getData().getSoftSkills()
			.stream().map(String::valueOf).collect(Collectors.toList()));

		return preqMapper.toResponseDto(cutQuestions, keywordAndSoftskillsInfo);
	}

	public KeywordAndSoftskillsResponseDto sendRequestToFlask(KeywordAndSoftskillsRequestDto requestDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=UTF-8");

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<KeywordAndSoftskillsResponseDto> responseEntity = restTemplate.postForEntity(
				FLASK_URL,
				new HttpEntity<>(requestDto, headers),
				KeywordAndSoftskillsResponseDto.class
			);

			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e);
		}
	}
}
