package kr.co.preq.domain.preq.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import kr.co.preq.domain.preq.dto.CoverLetterRequestDto;
import kr.co.preq.domain.preq.dto.CoverLetterResponseDto;
import kr.co.preq.domain.preq.dto.PreqRequestDto;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
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

	@GetMapping("/list")
	public ApiResponse<List<CoverLetterResponseDto>> getList() {
		List<CoverLetterResponseDto> responseDtoList = preqService.getPreqList();
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}

	@GetMapping("/{cletterId}")
	public ApiResponse<PreqResponseDto> getPreq(@PathVariable Long cletterId) {

		PreqResponseDto responseDto = preqService.getPreq(cletterId);

		return ApiResponse.success(SuccessCode.PREQ_GET_SUCCESS, responseDto);
	}
}
