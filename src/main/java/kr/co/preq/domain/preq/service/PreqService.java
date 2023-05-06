package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.domain.preq.dto.CoverLetterMapper;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.dto.CoverLetterRequestDto;
import kr.co.preq.domain.preq.dto.CoverLetterResponseDto;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final MemberService memberService;
	private final CoverLetterMapper coverLetterMapper;

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
	public PreqResponseDto getPreq(Long cletterId) {
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
}
