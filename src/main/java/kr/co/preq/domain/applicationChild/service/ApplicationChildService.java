package kr.co.preq.domain.applicationChild.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.preq.domain.applicationChild.dto.ApplicationChildMapper;
import kr.co.preq.domain.applicationChild.dto.ApplicationChildRequestDto;
import kr.co.preq.domain.applicationChild.dto.ApplicationChildResponseDto;
import kr.co.preq.domain.applicationChild.dto.CoverLetterResponseDto;
import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.applicationChild.repository.ApplicationChildRepository;
import kr.co.preq.domain.board.auth.service.AuthService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.preq.dto.PreqMapper;
import kr.co.preq.domain.preq.dto.PreqRequestDto;
import kr.co.preq.domain.preq.dto.PreqResponseDto;
import kr.co.preq.domain.preq.entity.Preq;
import kr.co.preq.domain.preq.repository.PreqRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationChildService {

	private final AuthService authService;
	private final ApplicationChildRepository applicationChildRepository;
	private final PreqRepository preqRepository;
	private final ApplicationChildMapper applicationChildMapper;

	@Transactional
	public Long saveApplicationChild(ApplicationChildRequestDto requestDto) {

		Member member = authService.findMember();

		ApplicationChild applicationChild = ApplicationChild.builder()
			.member(member)
			.question(requestDto.getQuestion())
			.answer(requestDto.getAnswer())
			// .keywords(requestDto.getKeywords())
			// .abilities(requestDto.getAbilities())
			.build();

		applicationChildRepository.save(applicationChild);

		// requestDto.getPreqList().forEach(q -> {
		// 	Preq preq = Preq.builder()
		// 		.question(q)
		// 		.applicationChild(applicationChild)
		// 		.build();
		// 	preqRepository.save(preq);
		// });
		//
		// List<Preq> preqList = preqRepository.findPreqsByApplicationChildId(applicationChild.getId());

		// return preqMapper.toResponseDto(preqList, applicationChild);

		return applicationChild.getId();
	}

	@Transactional(readOnly = true)
	public List<CoverLetterResponseDto> getApplicationChildList() {
		Member member = authService.findMember();

		List<ApplicationChild> applicationChildren = applicationChildRepository.findCoverLettersByMemberId(member.getId());
		return applicationChildren.stream()
			.map(applicationChildMapper::toResponseDto)
			.collect(Collectors.toList());
	}
}
