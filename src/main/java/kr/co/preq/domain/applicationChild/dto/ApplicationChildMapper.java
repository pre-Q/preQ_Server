package kr.co.preq.domain.applicationChild.dto;

import org.springframework.stereotype.Component;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationChildMapper {
	public CoverLetterResponseDto toResponseDto(ApplicationChild applicationChild) {
		if (applicationChild == null) return null;

		CoverLetterResponseDto.CoverLetterResponseDtoBuilder cLetterResponseDto = CoverLetterResponseDto.builder();
		cLetterResponseDto.id(applicationChild.getId());
		cLetterResponseDto.question(applicationChild.getQuestion());

		return cLetterResponseDto.build();
	}
}
