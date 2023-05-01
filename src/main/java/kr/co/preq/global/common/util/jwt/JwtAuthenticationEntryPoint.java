package kr.co.preq.global.common.util.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.ErrorCode;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		setResponse(response, ErrorCode.UNAUTHORIZED_USER);
	}

	private void setResponse(HttpServletResponse response, ErrorCode code) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ApiResponse<Object> apiResponse = ApiResponse.error(code);
		response.getWriter().println(mapper.writeValueAsString(apiResponse));
	}
}
