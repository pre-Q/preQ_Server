package kr.co.preq.domain.application.controller;

import kr.co.preq.domain.application.dto.response.ApplicationListGetResponseDto;
import kr.co.preq.domain.application.service.ApplicationService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
