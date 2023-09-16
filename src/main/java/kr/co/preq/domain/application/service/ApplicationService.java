package kr.co.preq.domain.application.service;

import kr.co.preq.domain.application.dto.response.ApplicationListGetResponseDto;
import kr.co.preq.domain.application.dto.request.ApplicationMemoUpdateRequestDto;
import kr.co.preq.domain.application.dto.request.ApplicationTitleUpdateRequestDto;
import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.application.repository.ApplicationRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.util.exception.BadRequestException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AuthService authService;

    public Long createApplication() {
        String title = "지원서 제목";
        String memo = "메모";
        Member member = authService.findMember();

        Application application = new Application(title, memo, member);
        applicationRepository.save(application);

        return application.getId();
    }

    @Transactional
    public List<ApplicationListGetResponseDto> getApplicationList() {
        Member member = authService.findMember();
        Long memberId = member.getId();

        return applicationRepository.findAllByMemberIdOrderByCreatedAt(memberId).stream()
                .map(ApplicationListGetResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateApplicationTitle(Long applicationId, ApplicationTitleUpdateRequestDto requestDto) {
        Member member = authService.findMember();
        Application application = applicationRepository.findByIdAndMemberId(applicationId, member.getId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_PARAMETER));

        application.updateTitle(requestDto.getTitle());
    }

    @Transactional
    public void updateApplicationMemo(Long applicationId, ApplicationMemoUpdateRequestDto requestDto) {
        Member member = authService.findMember();
        Application application = applicationRepository.findByIdAndMemberId(applicationId, member.getId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_PARAMETER));

        application.updateMemo(requestDto.getMemo());
    }
}
