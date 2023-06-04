package kr.co.preq.domain.preq.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreqRequestDto {
	@NotBlank(message = "문항이 없습니다.")
	private String question;

	@NotBlank(message = "답변이 없습니다.")
	private String answer;

	@NotNull(message = "예상 질문이 없습니다.")
	private List<String> preqList;

	@NotNull(message = "키워드가 없습니다.")
	private List<String> keywords;

	@NotNull(message = "역량이 없습니다.")
	private List<String> abilities;
}
