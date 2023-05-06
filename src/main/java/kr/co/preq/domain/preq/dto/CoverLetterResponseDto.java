package kr.co.preq.domain.preq.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoverLetterResponseDto {
	@JsonProperty("id")
	private Long id;

	private Long userId;

	private String question;

	private String answer;
}
