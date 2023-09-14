package kr.co.preq.domain.member.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.preq.domain.member.dto.response.MemberResponseDto;
import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Slf4j
public class MemberController {
	private final MemberService memberService;

	@GetMapping("")
	public ApiResponse<MemberResponseDto> getProfile() {
		MemberResponseDto responseDto = memberService.getProfile();
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDto);
	}

	@DeleteMapping("")
	public ApiResponse<MemberResponseDto> deleteMember() {
		MemberResponseDto responseDto = memberService.deleteProfile();
		return ApiResponse.success(SuccessCode.DELETE_ACCOUNT_SUCCESS, responseDto);
	}
}
