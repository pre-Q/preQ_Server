package kr.co.preq.domain.preq.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/")
	public ApiResponse<CoverLetterResponseDto> saveCoverLetter(@ModelAttribute @Valid CoverLetterRequestDto requestDto) {
		CoverLetterResponseDto responseDto = preqService.saveCoverLetter(requestDto);
		return ApiResponse.success(SuccessCode.COVERLETTER_CREATE_SUCCESS, responseDto);
	}

}
