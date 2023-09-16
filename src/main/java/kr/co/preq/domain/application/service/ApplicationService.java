package kr.co.preq.domain.application.service;

import kr.co.preq.domain.application.dto.response.ApplicationGetResponseDto;
import kr.co.preq.domain.application.dto.response.ApplicationListGetResponseDto;
import kr.co.preq.domain.application.dto.request.ApplicationMemoUpdateRequestDto;
import kr.co.preq.domain.application.dto.request.ApplicationTitleUpdateRequestDto;
import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.application.repository.ApplicationRepository;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildResponseDto;
import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.applicationChild.repository.ApplicationChildRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.dto.request.KeywordAndSoftskillsRequestDto;
import kr.co.preq.domain.preq.dto.response.KeywordAndSoftskillsResponseDto;
import kr.co.preq.domain.preq.service.PreqService;
import kr.co.preq.global.common.util.exception.BadRequestException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AuthService authService;

    private final PreqService preqService;
    private final ApplicationChildRepository applicationChildRepository;

    public Long createApplication() {
        String title = "지원서 제목";
        String memo = "메모";

        Member member = authService.findMember();

        Application application = new Application(title, memo, member);
        applicationRepository.save(application);

        return application.getId();
    }

    @Transactional(readOnly = true)
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
        Long memberId = member.getId();
        Application application = getApplication(applicationId, memberId);

        application.updateTitle(requestDto.getTitle());
    }

    @Transactional
    public void updateApplicationMemo(Long applicationId, ApplicationMemoUpdateRequestDto requestDto) {
        Member member = authService.findMember();
        Long memberId = member.getId();
        Application application = getApplication(applicationId, memberId);

        application.updateMemo(requestDto.getMemo());
    }

    @Transactional(readOnly = true)
    public ApplicationGetResponseDto getDetailApplication(Long applicationId) {
        Member member = authService.findMember();
        Long memberId = member.getId();

        Application application = getApplication(applicationId, memberId);

        List<ApplicationChildResponseDto> applicationChild = applicationChildRepository.findApplicationChildByApplicationIdAndMemberIdOrderByCreatedAt(applicationId, memberId).stream()
                .map(aChild -> ApplicationChildResponseDto.of(aChild.getId(), aChild.getQuestion(), aChild.getAnswer()))
                .collect(Collectors.toList());

        StringBuilder allAnswer = new StringBuilder();
        for (ApplicationChild answer : applicationChildRepository.findApplicationChildByApplicationIdAndMemberIdOrderByCreatedAt(applicationId, memberId)) {
            allAnswer.append(answer.getAnswer());
        }

        // extract keywords
        KeywordAndSoftskillsResponseDto keywordAndSoftskillsInfo = preqService.sendRequestToFlask(KeywordAndSoftskillsRequestDto.builder()
                .application(allAnswer.toString())
                .build());

        // update application keywords and abilities
        application.updateKeywords(keywordAndSoftskillsInfo.getData().getKeywordTop5());
        application.updateAbilities(keywordAndSoftskillsInfo.getData().getSoftSkills()
                .stream().map(String::valueOf).collect(Collectors.toList()));

        List<String> resultKeywords = application.getKeywords();
        List<String> resultAbilities = application.getAbilities();

        return ApplicationGetResponseDto.builder()
                .id(application.getId())
                .title(application.getTitle())
                .memo(application.getMemo())
                .applicationChild(applicationChild)
                .keywordTop5(resultKeywords)
                .softSkills(resultAbilities)
                .build();
    }

    private Application getApplication(Long applicationId, Long memberId) {
        return applicationRepository.findByIdAndMemberId(applicationId, memberId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_PARAMETER));
    }
}
