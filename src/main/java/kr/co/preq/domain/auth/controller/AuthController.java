package kr.co.preq.domain.auth.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.preq.domain.auth.dto.AuthRequestDto;
import kr.co.preq.domain.auth.dto.AuthResponseDto;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private AuthService authService;

	@PostMapping("/login")
	public ApiResponse<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto requestDto) {
		AuthResponseDto responseDto = authService.login(requestDto);
		return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, responseDto);
	}
}
