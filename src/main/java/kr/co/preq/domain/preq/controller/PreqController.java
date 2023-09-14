package kr.co.preq.domain.preq.controller;

import kr.co.preq.domain.preq.dto.*;
import org.springframework.web.bind.annotation.*;

import kr.co.preq.domain.preq.service.PreqService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/preq")
@Slf4j
public class PreqController {
	private final PreqService preqService;

	@PostMapping("/generate/{applicationChildId}")
	public ApiResponse<PreqAndKeywordResponseDto> createPreqAndKeyword(@PathVariable Long applicationChildId) {
		PreqAndKeywordResponseDto responseDto = preqService.createPreqAndKeyword(applicationChildId);
		return ApiResponse.success(SuccessCode.PREQ_GET_SUCCESS, responseDto);
	}
}
