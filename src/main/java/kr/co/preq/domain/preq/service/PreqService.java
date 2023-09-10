package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.preq.dto.*;
import kr.co.preq.domain.preq.entity.Preq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.entity.ApplicationChild;
import kr.co.preq.domain.preq.repository.ApplicationChildRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import kr.co.preq.global.common.util.exception.CustomException;
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
	private final AuthService authService;
	private final PreqMapper preqMapper;

	@Value("${flask.url}") private String FLASK_URL;

	@Transactional
	public PreqResponseDto saveCoverLetter(PreqRequestDto requestDto) {

		Member member = authService.findMember();

		ApplicationChild applicationChild = ApplicationChild.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer())
			.keywords(requestDto.getKeywords())
			.abilities(requestDto.getAbilities())
			.build();

		applicationChildRepository.save(applicationChild);

		requestDto.getPreqList().forEach(q -> {
			Preq preq = Preq.builder()
				.question(q)
				.applicationChild(applicationChild)
				.build();
			preqRepository.save(preq);
		});

		List<Preq> preqList = preqRepository.findPreqsByApplicationChildId(applicationChild.getId());

		return preqMapper.toResponseDto(preqList, applicationChild);
	}

	@Transactional(readOnly = true)
	public List<CoverLetterResponseDto> getPreqList() {
		 Member member = authService.findMember();

		List<ApplicationChild> applicationChildren = applicationChildRepository.findCoverLettersByMemberId(member.getId());
		return applicationChildren.stream()
			.map(preqMapper::toResponseDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PreqResponseDto getPreq(Long achildId) {
		Member member = authService.findMember();

		ApplicationChild applicationChild = applicationChildRepository.findCoverLetterById(achildId)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

		if (!member.equals(applicationChild.getMember())) throw new CustomException(ErrorCode.NOT_AUTHORIZED);

		List<Preq> preqList = preqRepository.findPreqsByApplicationChildId(achildId);
		return preqMapper.toResponseDto(preqList, applicationChild);
	}

	@Transactional
	public PreqAndKeywordResponseDto getPreqAndKeyword(CoverLetterRequestDto requestDto) {
		List<String> questions = openAIService.generateQuestions(requestDto);
		List<String> cutQuestions = new ArrayList<>();
		questions.forEach(q -> {
			if (q.contains(": ")) {
				String[] str = q.split(": ");
				cutQuestions.add(str[1]);
			} else {
				cutQuestions.add(q);
			}
		});

		// send request to flask
		ApplicationResponseDto keywordInfo = sendRequestToFlask(ApplicationRequestDto.builder()
			.application(requestDto.getAnswer())
			.build());

		return preqMapper.toResponseDto(cutQuestions, keywordInfo);
	}

	private ApplicationResponseDto sendRequestToFlask(ApplicationRequestDto requestDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=UTF-8");

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ApplicationResponseDto> responseEntity = restTemplate.postForEntity(
				FLASK_URL,
				new HttpEntity<>(requestDto, headers),
				ApplicationResponseDto.class
			);

			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e);
		}
	}

}
