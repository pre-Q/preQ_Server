package kr.co.preq.domain.preq.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreqResponseDto {
	private Long id;
	private String question;
	private String answer;
	private List<PreqDto> preqList;
	private List<String> keywordTop5;
	private List<String> softSkills;
}
