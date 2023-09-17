package kr.co.preq.domain.applicationChild.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationChildRequestDto {
	@NotBlank(message = "문항이 없습니다.")
	private String question;

	@NotNull(message = "답변이 없습니다.")
	private String answer;
}
