package kr.co.preq.domain.application.controller;

import kr.co.preq.domain.application.dto.response.ApplicationGetResponseDto;
import kr.co.preq.domain.application.dto.response.ApplicationListGetResponseDto;
import kr.co.preq.domain.application.dto.request.ApplicationMemoUpdateRequestDto;
import kr.co.preq.domain.application.dto.request.ApplicationTitleUpdateRequestDto;
import kr.co.preq.domain.application.service.ApplicationService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
    
    @GetMapping("/list")
    public ApiResponse<List<ApplicationListGetResponseDto>> getApplicationList() {
        List<ApplicationListGetResponseDto> applicationList = applicationService.getApplicationList();
        
        return ApiResponse.success(SuccessCode.APPLICATION_LIST_GET_SUCCESS, applicationList);
    }

    @PatchMapping("/{applicationId}/title")
    public ApiResponse<Object> updateApplicationTitle(@PathVariable Long applicationId, @RequestBody ApplicationTitleUpdateRequestDto requestDto) {
        applicationService.updateApplicationTitle(applicationId, requestDto);
        
        return ApiResponse.success(SuccessCode.APPLICATION_TITLE_UPDATE_SUCCESS);
    }

    @PatchMapping("/{applicationId}/memo")
    public ApiResponse<Object> updateApplicationMemo(@PathVariable Long applicationId, @RequestBody ApplicationMemoUpdateRequestDto requestDto) {
        applicationService.updateApplicationMemo(applicationId, requestDto);
        
        return ApiResponse.success(SuccessCode.APPLICATION_MEMO_UPDATE_SUCCESS);
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationGetResponseDto> getDetailApplication(@PathVariable Long applicationId) {
        ApplicationGetResponseDto application = applicationService.getDetailApplication(applicationId);

        return ApiResponse.success(SuccessCode.APPLICATION_GET_SUCCESS, application);
    }

    @DeleteMapping("/{applicationId}")
    public ApiResponse<Object> deleteApplication(@PathVariable Long applicationId) {
        Long deletedApplicationId = applicationService.deleteApplication(applicationId);

        return ApiResponse.success(SuccessCode.APPLICATION_DELETE_SUCCESS, deletedApplicationId);
    }
}
