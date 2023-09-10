package kr.co.preq.domain.application.service;

import kr.co.preq.domain.application.dto.ApplicationCreateResponseDto;
import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.application.repository.ApplicationRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AuthService authService;

    public ApplicationCreateResponseDto createApplication() {
        String title = "지원서 제목";
        String memo = "메모";
        Member member = authService.findMember();

        Application application = new Application(title, memo, member);
        applicationRepository.save(application);

        return ApplicationCreateResponseDto.builder()
                .id(application.getId()).build();
    }
}
