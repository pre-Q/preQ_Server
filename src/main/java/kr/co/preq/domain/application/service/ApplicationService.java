package kr.co.preq.domain.application.service;

import kr.co.preq.domain.application.dto.ApplicationTitleUpdateRequestDto;
import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.application.repository.ApplicationRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
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

    public void updateApplicationTitle(Long applicationId, ApplicationTitleUpdateRequestDto requestDto) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NO_ID));

        application.setTitle(requestDto.getTitle());
    }
}
