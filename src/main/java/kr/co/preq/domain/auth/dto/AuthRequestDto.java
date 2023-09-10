package kr.co.preq.domain.board.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {

	@NotBlank(message = "타입이 없습니다.")
	private String type;

	@NotBlank(message = "accessToken이 없습니다.")
	private String accessToken;
}
