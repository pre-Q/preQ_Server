package kr.co.preq.domain.board.auth.controller;

import javax.validation.Valid;

import kr.co.preq.domain.board.auth.dto.AuthRequestDto;
import kr.co.preq.domain.board.auth.dto.AuthResponseDto;
import kr.co.preq.domain.board.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.preq.domain.board.auth.dto.LogoutRequestDto;
import kr.co.preq.domain.board.auth.dto.TokenRequestDto;
import kr.co.preq.global.common.util.jwt.TokenDto;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ApiResponse<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto requestDto) {
		AuthResponseDto responseDto = authService.login(requestDto);
		return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, responseDto);
	}

	@PostMapping("/refresh")
	public ApiResponse<TokenDto> refresh(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
		TokenDto response = authService.reissue(tokenRequestDto);
		return ApiResponse.success(SuccessCode.TOKEN_REISSUE_SUCCESS, response);
	}

	@PostMapping("/logout")
	public ApiResponse<Object> logout(@RequestBody @Valid LogoutRequestDto logoutRequestDto) {
		authService.logout(logoutRequestDto);
		return ApiResponse.success(SuccessCode.LOGOUT_SUCCESS, null);
	}
}
