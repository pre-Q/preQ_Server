package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.preq.dto.SessionDto;
import kr.co.preq.domain.preq.dto.Message;
import kr.co.preq.domain.preq.dto.request.KeywordAndSoftskillsRequestDto;
import kr.co.preq.domain.preq.dto.response.KeywordAndSoftskillsResponseDto;
import kr.co.preq.domain.preq.dto.response.PreqAndKeywordResponseDto;
import kr.co.preq.domain.preq.dto.response.PreqMapper;
import kr.co.preq.domain.preq.entity.Preq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.applicationChild.repository.ApplicationChildRepository;
import kr.co.preq.domain.preq.repository.PreqRepository;
import kr.co.preq.global.common.entity.BaseEntity;
import kr.co.preq.global.common.util.exception.BadRequestException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreqService {

	private final PreqRepository preqRepository;
	private final ApplicationChildRepository applicationChildRepository;
	private final OpenAIService openAIService;
	private final PreqMapper preqMapper;

	@Value("${flask.url}") private String FLASK_URL;

	@Transactional
	public PreqAndKeywordResponseDto createPreqAndKeyword(Long applicationChildId) {

		ApplicationChild applicationChild = applicationChildRepository.findById(applicationChildId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.NO_ID));

		List<ApplicationChild> allApplicationChilds = applicationChildRepository
			.findAllByApplicationChildIdOrderByParentIdAscNullsFirstCategoryIdAsc(applicationChildId);

		List<SessionDto> parentApplicationChilds = allApplicationChilds
			// .subList(0, allApplicationChilds.size()-1)	// remove first element (=self)
			.stream().map((a) -> {
				List<Preq> preqList = preqRepository.findPreqsByApplicationChildIdAndIsDeleted(a.getId(), false);
				return new SessionDto(a.getQuestion(), a.getAnswer(), preqList.stream().map((p) -> p.getQuestion()).collect(
					Collectors.toList()));
			}).collect(Collectors.toList());

		// generate preQuestions
		List<String> questions = openAIService.generateQuestions(applicationChild.getQuestion(), applicationChild.getAnswer(), parentApplicationChilds);

		// manufacturing chatGPT response
		List<String> cutQuestions = new ArrayList<>();
		questions.forEach(q -> {
				if (q.contains(": ")) {
					String[] str = q.split(": ");
					cutQuestions.add(str[1].replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9,.? ]", ""));
				} else {
					cutQuestions.add(q.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9,.? ]", ""));
				}
			}
		);

		// soft delete old preQuestions
		List<Preq> oldPreqList = preqRepository.findPreqsByApplicationChildIdAndIsDeleted(applicationChildId, false);
		if (!oldPreqList.isEmpty()) {
			oldPreqList.forEach(BaseEntity::softDelete);
		}

		// insert generated preQuestions into DB
		cutQuestions.forEach(q -> {
			Preq preq = Preq.builder()
				.question(q)
				.applicationChild(applicationChild)
				.build();
			preqRepository.save(preq);
		});

		// extract keywords
		KeywordAndSoftskillsResponseDto keywordAndSoftskillsInfo = sendRequestToFlask(KeywordAndSoftskillsRequestDto.builder()
			.application(applicationChild.getAnswer())
			.build());

		// update application child keywords and abilities
		applicationChild.updateKeywords(keywordAndSoftskillsInfo.getData().getKeywordTop5());
		applicationChild.updateAbilities(keywordAndSoftskillsInfo.getData().getSoftSkills()
			.stream().map(String::valueOf).collect(Collectors.toList()));

		return preqMapper.toResponseDto(cutQuestions, keywordAndSoftskillsInfo);
	}

	public KeywordAndSoftskillsResponseDto sendRequestToFlask(KeywordAndSoftskillsRequestDto requestDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=UTF-8");

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<KeywordAndSoftskillsResponseDto> responseEntity = restTemplate.postForEntity(
				FLASK_URL,
				new HttpEntity<>(requestDto, headers),
				KeywordAndSoftskillsResponseDto.class
			);

			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e);
		}
	}
}
