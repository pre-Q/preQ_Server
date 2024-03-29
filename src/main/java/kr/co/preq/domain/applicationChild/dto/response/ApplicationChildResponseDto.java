package kr.co.preq.domain.applicationChild.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationChildResponseDto {
	private Long applicationChildId;
	private String question;
	private String answer;

	public static ApplicationChildResponseDto of(Long applicationChildId, String question, String answer) {
		return new ApplicationChildResponseDto(applicationChildId, question, answer);
	}
}
