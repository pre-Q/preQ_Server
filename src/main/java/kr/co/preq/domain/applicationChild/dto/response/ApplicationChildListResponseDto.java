package kr.co.preq.domain.applicationChild.dto.response;

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
public class ApplicationChildListResponseDto {
	private Long id;
	private String question;
	private String answer;
}
