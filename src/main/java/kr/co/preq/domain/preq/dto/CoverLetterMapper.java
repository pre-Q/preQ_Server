package kr.co.preq.domain.preq.dto;

import org.springframework.stereotype.Component;

import kr.co.preq.domain.preq.entity.CoverLetter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CoverLetterMapper {

	public CoverLetterResponseDto toResponseDto(CoverLetter coverLetter) {
		if (coverLetter == null) return null;

		CoverLetterResponseDto.CoverLetterResponseDtoBuilder cLetterResponseDto = CoverLetterResponseDto.builder();
		cLetterResponseDto.id(coverLetter.getId());
		cLetterResponseDto.question(coverLetter.getQuestion());
		cLetterResponseDto.answer(coverLetter.getAnswer());

		return cLetterResponseDto.build();
	}
}
