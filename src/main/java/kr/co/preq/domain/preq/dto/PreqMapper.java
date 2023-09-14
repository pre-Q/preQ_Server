package kr.co.preq.domain.preq.dto;

import java.util.ArrayList;
import java.util.List;

import kr.co.preq.domain.applicationChild.dto.ApplicationResponseDto;
import kr.co.preq.domain.applicationChild.dto.CoverLetterResponseDto;
import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.domain.preq.entity.Preq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreqMapper {

    public PreqResponseDto toResponseDto(List<Preq> preqList, ApplicationChild applicationChild) {
        if (applicationChild == null) return null;

        PreqResponseDto.PreqResponseDtoBuilder builder = PreqResponseDto.builder();
        builder.id(applicationChild.getId());
        builder.question(applicationChild.getQuestion());
        builder.answer(applicationChild.getAnswer());
        List<PreqDto> preqDtoList = new ArrayList<>();
        for (Preq preq : preqList) {
            preqDtoList.add(PreqDto.builder()
                .id(preq.getId())
                .question((preq.getQuestion()))
                .build());
        }
        builder.preqList(preqDtoList);
        builder.keywordTop5(applicationChild.getKeywords());
        builder.softSkills(applicationChild.getAbilities());

        return builder.build();
    }

    public PreqAndKeywordResponseDto toResponseDto(List<String> questions, ApplicationResponseDto keywordInfo) {
        if (questions == null) return null;

        PreqAndKeywordResponseDto.PreqAndKeywordResponseDtoBuilder builder = PreqAndKeywordResponseDto.builder();
        builder.preqList(questions);
        builder.keywordTop5(keywordInfo.getData().getKeywordTop5());
        builder.softSkills(keywordInfo.getData().getSoftSkills());

        return builder.build();
    }
}
