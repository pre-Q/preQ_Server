package kr.co.preq.domain.applicationChild.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.preq.domain.applicationChild.dto.ApplicationChildRequestDto;
import kr.co.preq.domain.applicationChild.dto.CoverLetterResponseDto;
import kr.co.preq.domain.applicationChild.service.ApplicationChildService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplicationChildController {

	private final ApplicationChildService applicationChildService;

	@PostMapping("")
	public ApiResponse<Object> saveCoverLetter(@RequestBody @Valid ApplicationChildRequestDto requestDto) {
		Long applicationChildId = applicationChildService.saveApplicationChild(requestDto);
		return ApiResponse.success(SuccessCode.COVERLETTER_CREATE_SUCCESS, applicationChildId);
	}

	@GetMapping("/list")
	public ApiResponse<List<CoverLetterResponseDto>> getList() {
		List<CoverLetterResponseDto> responseDtoList = applicationChildService.getApplicationChildList();
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}
}
