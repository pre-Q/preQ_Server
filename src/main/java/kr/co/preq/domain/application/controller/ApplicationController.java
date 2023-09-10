package kr.co.preq.domain.application.controller;

import kr.co.preq.domain.application.dto.ApplicationMemoUpdateRequestDto;
import kr.co.preq.domain.application.dto.ApplicationTitleUpdateRequestDto;
import kr.co.preq.domain.application.service.ApplicationService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ApiResponse<Object> createApplication() {
        Long applicationId = applicationService.createApplication();

        return ApiResponse.success(SuccessCode.APPLICATION_CREATE_SUCCESS, applicationId);
    }

    @PatchMapping("/{applicationId}/title")
    public ApiResponse<Object> updateApplicationTitle(@RequestParam Long applicationId, @RequestBody ApplicationTitleUpdateRequestDto requestDto) {
        applicationService.updateApplicationTitle(applicationId, requestDto);
        return ApiResponse.success(SuccessCode.APPLICATION_TITLE_UPDATE_SUCCESS);
    }

    @PatchMapping("/{applicationId}/memo")
    public ApiResponse<Object> updateApplicationMemo(@RequestParam Long applicationId, @RequestBody ApplicationMemoUpdateRequestDto requestDto) {
        applicationService.updateApplicationMemo(applicationId, requestDto);
        return ApiResponse.success(SuccessCode.APPLICATION_MEMO_UPDATE_SUCCESS);
    }
}
