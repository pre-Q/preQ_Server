package kr.co.preq.domain.preq.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreqRequestDto {

	@NotBlank(message = "문항이 없습니다.")
	private String question;

	@NotBlank(message = "답변이 없습니다.")
	private String answer;
}
