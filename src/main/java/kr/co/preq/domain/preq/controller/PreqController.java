package kr.co.preq.domain.preq.controller;

import javax.validation.Valid;

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

	@PostMapping()
	public ApiResponse<CoverLetterResponseDto> saveCoverLetter(@RequestBody @Valid CoverLetterRequestDto requestDto) {

		CoverLetterResponseDto responseDto = preqService.saveCoverLetter(requestDto);

		return ApiResponse.success(SuccessCode.COVERLETTER_CREATE_SUCCESS, responseDto);
	}

	@GetMapping("/{cletterId}")
	public ApiResponse<CoverLetterResponseDto> getPreq(@PathVariable Long cletterId) {

		PreqResponseDto responseDto = preqService.getPreq(cletterId);

		//return ApiResponse.success(SuccessCode.PREQ_GET_SUCCESS, responseDto);
		return null;
	}

	@PostMapping("/gpt")
	public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
		return preqService.askQuestion(requestDto);
	}
}
