package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.domain.preq.dto.CoverLetterMapper;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.OpenAiConfig;
import kr.co.preq.domain.preq.dto.*;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final MemberService memberService;
	private final CoverLetterMapper coverLetterMapper;
	private final PreqMapper preqMapper;

	@Transactional
	public CoverLetterResponseDto saveCoverLetter(CoverLetterRequestDto requestDto) {

		Member member = memberService.findMember();

		CoverLetter coverLetter = CoverLetter.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer()).
			build();

		coverLetterRepository.save(coverLetter);
		return coverLetterMapper.toResponseDto(coverLetter);
	}

	@Transactional(readOnly = true)
	public OpenAIResponseDto getPreq(Long cletterId) {
		Member member = memberService.findMember();

		//TODO: refactoring -> parameter valid check and throw ApiResponse.error at controller
		CoverLetter coverLetter = coverLetterRepository.findById(cletterId)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

		String cQuestion = coverLetter.getQuestion();
		String cAnwser = coverLetter.getAnswer();


		//List<PreqResponseDto> preqResponseList = preqRepository.
		return null;
	}

	 @Transactional(readOnly = true)
	public List<CoverLetterResponseDto> getPreqList() {
		Member member = memberService.findMember();

		List<CoverLetter> coverLetters = coverLetterRepository.findCoverLettersByMemberId(member.getId());
		List<CoverLetterResponseDto> result = coverLetters.stream()
			.map(x -> coverLetterMapper.toResponseDto(x))
			.collect(Collectors.toList());
		return result;
	}

	RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	@Value("${openai.api.key}")
	String API_KEY;

	public HttpEntity<OpenAIRequestDto> buildHttpEntity(OpenAIRequestDto requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(OpenAiConfig.MEDIA_TYPE));
		headers.add(OpenAiConfig.AUTHORIZATION, OpenAiConfig.BEARER + API_KEY);
		return new HttpEntity<>(requestDto, headers);
	}

	public OpenAIResponseDto getResponse(HttpEntity<OpenAIRequestDto> chatGptRequestDtoHttpEntity) {
		ResponseEntity<OpenAIResponseDto> responseEntity = restTemplate.postForEntity(
				OpenAiConfig.URL,
				chatGptRequestDtoHttpEntity,
				OpenAIResponseDto.class);

		return responseEntity.getBody();
	}

	public List<PreqResponseDto> askQuestion(Long cletterId) {
		CoverLetter coverLetter = coverLetterRepository.findCoverLetterById(cletterId)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

		String command = "너는 면접관이고, 지원자의 지원서를 보고 면접 질문을 하는 거야. 다음 지원서를 읽고 면접 질문을 한가지 추천해줘." + coverLetter.getAnswer();
		System.out.println(command);

		OpenAIResponseDto response = this.getResponse(
				this.buildHttpEntity(
						new OpenAIRequestDto(
								OpenAiConfig.MODEL,
								OpenAiConfig.N,
								command
						)
				)
		);

		List<Choice> preqLists = response.getChoices();

		for (Choice preqList : preqLists) {
			String ques = preqList.getMessage().getContent();

			Preq preq = Preq.builder()
					.question(ques)
					.coverLetter(coverLetter).
					build();

			preqRepository.save(preq);
		}

		return getPreqList(cletterId);

	}

	public List<PreqResponseDto> getPreqList(Long cletterId) {

		List<Preq> preqs = preqRepository.findPreqsByCoverLetterId(cletterId);

		List<PreqResponseDto> result = preqs.stream()
				.map(x -> preqMapper.toResponseDto(x))
				.collect(Collectors.toList());
		return result;
	}

}
