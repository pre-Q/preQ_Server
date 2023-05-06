package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.preq.dto.CoverLetterRequestDto;
import kr.co.preq.domain.preq.dto.CoverLetterResponseDto;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

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
}
