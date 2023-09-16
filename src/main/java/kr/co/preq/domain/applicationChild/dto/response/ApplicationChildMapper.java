package kr.co.preq.domain.applicationChild.dto.response;

import java.util.ArrayList;
import java.util.List;

import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildInfoResponseDto;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildListResponseDto;
import org.springframework.stereotype.Component;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.preq.dto.PreqDto;
import kr.co.preq.domain.preq.entity.Preq;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationChildMapper {
	public ApplicationChildListResponseDto toResponseDto(ApplicationChild applicationChild) {
		if (applicationChild == null) return null;

		ApplicationChildListResponseDto.ApplicationChildListResponseDtoBuilder cLetterResponseDto = ApplicationChildListResponseDto.builder();
		cLetterResponseDto.id(applicationChild.getId());
		cLetterResponseDto.question(applicationChild.getQuestion());
		cLetterResponseDto.answer(applicationChild.getAnswer());

		return cLetterResponseDto.build();
	}

	public ApplicationChildInfoResponseDto toResponseDto(ApplicationChild applicationChild, List<Preq> preqList) {
		if (applicationChild == null) return null;

		ApplicationChildInfoResponseDto.ApplicationChildInfoResponseDtoBuilder builder = ApplicationChildInfoResponseDto.builder();
		builder.id(applicationChild.getId());
		builder.question(applicationChild.getQuestion());
		builder.answer(applicationChild.getAnswer());
		List<PreqDto> preqDtoList = new ArrayList<>();
		for (Preq preq : preqList) {
			preqDtoList.add(PreqDto.builder()
				.id(preq.getId())
				.question((preq.getQuestion()))
				.build());
		}
		builder.preqList(preqDtoList);
		builder.keywordTop5(applicationChild.getKeywords());
		builder.softSkills(applicationChild.getAbilities());

		return builder.build();
	}
}
