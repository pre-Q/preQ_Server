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
public class CoverLetterAndPreqResponseDto {
	private Long id;
	private String question;
	private String answer;
	private List<PreqResponseDto> preqList;
}
