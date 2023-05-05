package kr.co.preq.domain.preq.service;

import java.util.Optional;

import kr.co.preq.domain.auth.service.AuthService;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.preq.dto.CoverLetterRequestDto;
import kr.co.preq.domain.preq.dto.CoverLetterResponseDto;
import kr.co.preq.domain.preq.dto.PreqRequestDto;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.repository.CoverLetterRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final CoverLetterRepository coverLetterRepository;
	private final MemberRepository memberRepository;	//TODO: delete
	private final AuthService authService;

	public CoverLetterResponseDto saveCoverLetter(CoverLetterRequestDto requestDto) {

		//TODO: find member and valid Logic

		Member member = authService.findMember();

		CoverLetter coverLetter = CoverLetter.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer()).
			build();

		coverLetterRepository.save(coverLetter);
		CoverLetterResponseDto coverLetterResponseDto = new CoverLetterResponseDto(coverLetter.getQuestion(), coverLetter.getAnswer());
		return coverLetterResponseDto;
	}
}
