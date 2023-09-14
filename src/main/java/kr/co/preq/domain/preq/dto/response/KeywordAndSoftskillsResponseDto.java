package kr.co.preq.domain.preq.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordAndSoftskillsResponseDto {
	private int status;
	private String message;
	private dataObject data;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class dataObject {
		private List<String> keywordTop5;
		private List<Float> softSkills;
	}
}
