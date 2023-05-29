package kr.co.preq.domain.preq.dto;

import java.util.ArrayList;
import java.util.List;

import kr.co.preq.domain.preq.entity.CoverLetter;
import kr.co.preq.domain.preq.entity.Preq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreqMapper {
    public CoverLetterAndPreqResponseDto toResponseDto(CoverLetter coverLetter, List<Preq> preqList, ApplicationResponseDto keywordInfo) {
        if (coverLetter == null || preqList == null) return null;

        CoverLetterAndPreqResponseDto.CoverLetterAndPreqResponseDtoBuilder builder = CoverLetterAndPreqResponseDto.builder();
        builder.id(coverLetter.getId());
        builder.question(coverLetter.getQuestion());
        builder.answer(coverLetter.getAnswer());

        List<PreqResponseDto> preqDtoList = new ArrayList<>();
        for (Preq preq : preqList) {
            preqDtoList.add(PreqResponseDto.builder()
                .id(preq.getId())
                .question((preq.getQuestion()))
                .build());
        }
        builder.preqList(preqDtoList);
        builder.keywordTop5(keywordInfo.getData().getKeywordTop5());
        builder.softSkills(keywordInfo.getData().getSoftSkills());

        return builder.build();
    }
}
