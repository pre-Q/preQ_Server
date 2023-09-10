package kr.co.preq.domain.application.controller;

import kr.co.preq.domain.application.service.ApplicationService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
