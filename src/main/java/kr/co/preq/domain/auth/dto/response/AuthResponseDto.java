package kr.co.preq.domain.auth.dto.response;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.util.jwt.TokenDto;
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

	public static AuthResponseDto from(TokenDto tokenDto, Member member) {
		AuthResponseDto authResponseDto = new AuthResponseDto();
		authResponseDto.userId = member.getId();
		authResponseDto.accessToken = tokenDto.getAccessToken();
		authResponseDto.refreshToken = tokenDto.getRefreshToken();
		authResponseDto.name = member.getName();
		authResponseDto.email = member.getEmail();
		return authResponseDto;
	}
}
