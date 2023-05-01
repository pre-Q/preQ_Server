package kr.co.preq.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDto {

	private Long userId;
	private String accessToken;
	private String refreshToken;
	private String name;
	private String email;

}
