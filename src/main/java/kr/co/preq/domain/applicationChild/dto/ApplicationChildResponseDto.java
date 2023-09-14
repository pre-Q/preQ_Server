package kr.co.preq.domain.applicationChild.dto;


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
}
