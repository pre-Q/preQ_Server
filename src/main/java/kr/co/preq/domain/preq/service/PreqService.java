package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.dto.CoverLetterRequestDto;
import kr.co.preq.domain.preq.dto.CoverLetterResponseDto;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final MemberService memberService;

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

	public PreqResponseDto getPreq(Long cletterId) {
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
}
