package kr.co.preq.domain.applicationChild.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildInfoResponseDto;
import kr.co.preq.domain.applicationChild.dto.request.ApplicationChildRequestDto;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildListResponseDto;
import kr.co.preq.domain.applicationChild.service.ApplicationChildService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
public class ApplicationChildController {

	private final ApplicationChildService applicationChildService;

	@PostMapping("/{applicationId}/child")
	public ApiResponse<Object> createApplicationChild(@PathVariable Long applicationId, @RequestBody @Valid ApplicationChildRequestDto requestDto) {
		Long applicationChildId = applicationChildService.saveApplicationChild(applicationId, requestDto);
		return ApiResponse.success(SuccessCode.APPLICATIONCHILD_CREATE_SUCCESS, applicationChildId);
	}

	@GetMapping("/{applicationId}/child/list")
	public ApiResponse<List<ApplicationChildListResponseDto>> getList(@PathVariable Long applicationId) {
		List<ApplicationChildListResponseDto> responseDtoList = applicationChildService.getApplicationChildList(applicationId);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}

	@GetMapping("/{applicationId}/child/{applicationChildId}")
	public ApiResponse<ApplicationChildInfoResponseDto> getApplicationChildInfo(@PathVariable Long applicationId, @PathVariable Long applicationChildId) {
		ApplicationChildInfoResponseDto responseDtoList = applicationChildService.getApplicationChildInfo(applicationId, applicationChildId);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}
}
