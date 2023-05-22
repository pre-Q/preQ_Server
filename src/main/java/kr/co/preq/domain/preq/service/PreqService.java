package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.preq.dto.CoverLetterMapper;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.dto.*;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;

import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final OpenAIService openAIService;
	private final AuthService authService;
	private final CoverLetterMapper coverLetterMapper;
	private final PreqMapper preqMapper;

	@Transactional
	public CoverLetterResponseDto saveCoverLetter(CoverLetterRequestDto requestDto) {

		Member member = authService.findMember();

		CoverLetter coverLetter = CoverLetter.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer()).
			build();

		coverLetterRepository.save(coverLetter);
		return coverLetterMapper.toResponseDto(coverLetter);
	}

	@Transactional(readOnly = true)
	public List<CoverLetterResponseDto> getPreqList() {
		 Member member = authService.findMember();

		List<CoverLetter> coverLetters = coverLetterRepository.findCoverLettersByMemberId(member.getId());
		return coverLetters.stream()
			.map(coverLetterMapper::toResponseDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public CoverLetterAndPreqResponseDto getCoverLetterAndPreqList(Long cletterId) {
		CoverLetter coverLetter = coverLetterRepository.findCoverLetterById(cletterId)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

		List<String> questions = openAIService.generateQuestions(coverLetter);

		questions.forEach(q -> {
			Preq preq = Preq.builder()
				.question(q)
				.coverLetter(coverLetter)
				.build();
			preqRepository.save(preq);
		});

		List<Preq> preqList = preqRepository.findPreqsByCoverLetterId(cletterId);

		return preqMapper.toResponseDto(coverLetter, preqList);
	}

}
