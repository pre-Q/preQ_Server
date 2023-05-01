package kr.co.preq.global.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;

public class SecurityUtil {

	private SecurityUtil() {
	}

	public static String getCurrentUserEmail() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}

		return authentication.getName();
	}
}