package kr.co.preq.domain.applicationChild.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.application.repository.ApplicationRepository;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildInfoResponseDto;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildMapper;
import kr.co.preq.domain.applicationChild.dto.request.ApplicationChildRequestDto;
import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildListResponseDto;
import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.applicationChild.repository.ApplicationChildRepository;
import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.domain.preq.repository.PreqRepository;
import kr.co.preq.global.common.util.exception.BadRequestException;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationChildService {

	private final AuthService authService;
	private final ApplicationChildRepository applicationChildRepository;
	private final ApplicationRepository applicationRepository;
	private final PreqRepository preqRepository;
	private final ApplicationChildMapper applicationChildMapper;

	@Transactional
	public Long saveApplicationChild(Long applicationId, ApplicationChildRequestDto requestDto) {
		Member member = authService.findMember();

		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.NO_ID));

		ApplicationChild applicationChild = ApplicationChild.builder()
			.application(application)
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer())
			.build();

		applicationChildRepository.save(applicationChild);

		return applicationChild.getId();
	}

	@Transactional(readOnly = true)
	public List<ApplicationChildListResponseDto> getApplicationChildList(Long applicationId) {
		Member member = authService.findMember();

		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.NO_ID));

		List<ApplicationChild> applicationChildren = applicationChildRepository
			.findApplicationChildByApplicationIdAndMemberIdOrderByCreatedAt(applicationId, member.getId());

		return applicationChildren.stream()
			.map(applicationChildMapper::toResponseDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ApplicationChildInfoResponseDto getApplicationChildInfo(Long applicationId, Long applicationChildId) {
		Member member = authService.findMember();

		ApplicationChild applicationChild = applicationChildRepository
			.findApplicationChildByIdAndApplicationIdAndMemberId(applicationChildId, applicationId, member.getId())
			.orElseThrow(() -> new BadRequestException(ErrorCode.BAD_PARAMETER));

		if (!member.equals(applicationChild.getMember())) throw new CustomException(ErrorCode.NOT_AUTHORIZED);

		List<Preq> preqList = preqRepository.findPreqsByApplicationChildIdAndIsDeleted(applicationChildId, false);

		return applicationChildMapper.toResponseDto(applicationChild, preqList);
	}
}
