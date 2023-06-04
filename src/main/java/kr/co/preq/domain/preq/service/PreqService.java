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
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
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
	private final CoverLetterRepository coverLetterRepository;
	private final OpenAIService openAIService;
	private final AuthService authService;
	private final PreqMapper preqMapper;

	@Value("${flask.url}") private String FLASK_URL;

	@Transactional
	public PreqResponseDto saveCoverLetter(PreqRequestDto requestDto) {

		Member member = authService.findMember();

		CoverLetter coverLetter = CoverLetter.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer())
			.keywords(requestDto.getKeywords())
			.abilities(requestDto.getAbilities())
			.build();

		coverLetterRepository.save(coverLetter);

		requestDto.getPreqList().forEach(q -> {
			Preq preq = Preq.builder()
				.question(q)
				.coverLetter(coverLetter)
				.build();
			preqRepository.save(preq);
		});

		List<Preq> preqList = preqRepository.findPreqsByCoverLetterId(coverLetter.getId());

		return preqMapper.toResponseDto(preqList, coverLetter);
	}

	@Transactional(readOnly = true)
	public List<CoverLetterResponseDto> getPreqList() {
		 Member member = authService.findMember();

		List<CoverLetter> coverLetters = coverLetterRepository.findCoverLettersByMemberId(member.getId());
		return coverLetters.stream()
			.map(preqMapper::toResponseDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PreqResponseDto getPreq(Long cletterId) {
		Member member = authService.findMember();

		CoverLetter coverLetter = coverLetterRepository.findCoverLetterById(cletterId)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

		if (!member.equals(coverLetter.getMember())) throw new CustomException(ErrorCode.NOT_AUTHORIZED);

		List<Preq> preqList = preqRepository.findPreqsByCoverLetterId(cletterId);
		return preqMapper.toResponseDto(preqList, coverLetter);
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
