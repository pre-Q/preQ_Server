package kr.co.preq.domain.preq.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PreqAndKeywordResponseDto {
	private List<String> preqList;
	private List<String> keywordTop5;
	private List<Float> softSkills;
}
