package kr.co.preq.global.common.util.exception;

import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode code;

	public CustomException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
