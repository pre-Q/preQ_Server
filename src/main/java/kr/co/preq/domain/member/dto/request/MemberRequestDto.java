package kr.co.preq.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

	@NotBlank(message = "이름이 없습니다.")
	private String name;

	@NotBlank(message = "이메일이 없습니다.")
	private String email;
}
