package kr.co.preq.domain.preq.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverLetterRequestDto {
	@NotBlank(message = "문항이 없습니다.")
	private String question;

	@NotBlank(message = "답변이 없습니다.")
	private String answer;
}
