package kr.co.preq.domain.applicationChild.dto;

import java.util.List;

import kr.co.preq.domain.preq.dto.PreqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CoverLetterAndPreqResponseDto {
	private Long id;
	private String question;
	private String answer;
	private List<PreqDto> preqList;
	private List<String> keywordTop5;
	private List<Float> softSkills;
}
