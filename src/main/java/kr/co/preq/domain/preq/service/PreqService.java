package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.domain.preq.ChatGptConfig;
import kr.co.preq.domain.preq.dto.*;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
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
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final MemberService memberService;

	private final PreqMapper preqMapper;

	public CoverLetterResponseDto saveCoverLetter(CoverLetterRequestDto requestDto) {

		Member member = memberService.findMember();

		CoverLetter coverLetter = CoverLetter.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer()).
			build();

		coverLetterRepository.save(coverLetter);
		CoverLetterResponseDto coverLetterResponseDto = new CoverLetterResponseDto(coverLetter.getId(), member.getId(), coverLetter.getQuestion(), coverLetter.getAnswer());
		return coverLetterResponseDto;
	}

	public OpenAIResponseDto getPreq(Long cletterId) {
		Member member = memberService.findMember();

		CoverLetter coverLetter = findCoverLetterById(cletterId);

		String cQuestion = coverLetter.getQuestion();
		String cAnwser = coverLetter.getAnswer();


		//List<PreqResponseDto> preqResponseList = preqRepository.
		return null;
	}

	private CoverLetter findCoverLetterById(Long cletterId) {
		return coverLetterRepository.findById(cletterId)
				.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));
	}

	RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	public HttpEntity<OpenAIRequestDto> buildHttpEntity(OpenAIRequestDto requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
		headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
		return new HttpEntity<>(requestDto, headers);
	}

	public OpenAIResponseDto getResponse(HttpEntity<OpenAIRequestDto> chatGptRequestDtoHttpEntity) {
		ResponseEntity<OpenAIResponseDto> responseEntity = restTemplate.postForEntity(
				ChatGptConfig.URL,
				chatGptRequestDtoHttpEntity,
				OpenAIResponseDto.class);

		return responseEntity.getBody();
	}

	public List<PreqResponseDto> askQuestion(Long cletterId) {
		CoverLetter coverLetter = findCoverLetterById(cletterId);

		String command = "너는 면접관이고, 지원자의 지원서를 보고 면접 질문을 하는 거야. 다음 지원서를 읽고 면접 질문을 한가지 추천해줘." + coverLetter.getAnswer();
		System.out.println(command);

		OpenAIResponseDto response = this.getResponse(
				this.buildHttpEntity(
						new OpenAIRequestDto(
								ChatGptConfig.MODEL,
								ChatGptConfig.N,
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
