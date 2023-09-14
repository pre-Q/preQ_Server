package kr.co.preq.domain.preq.controller;

import javax.validation.Valid;

import kr.co.preq.domain.preq.dto.*;
import org.springframework.web.bind.annotation.*;

import kr.co.preq.domain.preq.service.PreqService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/preq")
@Slf4j
public class PreqController {
	private final PreqService preqService;

	@GetMapping("/{cletterId}")
	public ApiResponse<PreqResponseDto> getPreq(@PathVariable Long cletterId) {
		PreqResponseDto responseDtoList = preqService.getPreq(cletterId);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}

	@PostMapping("/result")
	public ApiResponse<PreqAndKeywordResponseDto> sendQuestion(@RequestBody @Valid CoverLetterRequestDto requestDto) {
		PreqAndKeywordResponseDto responseDto = preqService.getPreqAndKeyword(requestDto);
		return ApiResponse.success(SuccessCode.PREQ_GET_SUCCESS, responseDto);
	}
}
