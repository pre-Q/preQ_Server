package kr.co.preq.global.common.util.exception;

import kr.co.preq.global.common.util.response.ErrorCode;

public class NumberFormatException extends RuntimeException {
	private final ErrorCode code;

	public NumberFormatException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}