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

    public CoverLetterResponseDto toResponseDto(CoverLetter coverLetter) {
        if (coverLetter == null) return null;

        CoverLetterResponseDto.CoverLetterResponseDtoBuilder cLetterResponseDto = CoverLetterResponseDto.builder();
        cLetterResponseDto.id(coverLetter.getId());
        cLetterResponseDto.question(coverLetter.getQuestion());

        return cLetterResponseDto.build();
    }

    public PreqResponseDto toResponseDto(List<Preq> preqList, CoverLetter coverLetter) {
        if (coverLetter == null) return null;

        PreqResponseDto.PreqResponseDtoBuilder builder = PreqResponseDto.builder();
        builder.id(coverLetter.getId());
        builder.question(coverLetter.getQuestion());
        builder.answer(coverLetter.getAnswer());
        List<PreqDto> preqDtoList = new ArrayList<>();
        for (Preq preq : preqList) {
            preqDtoList.add(PreqDto.builder()
                .id(preq.getId())
                .question((preq.getQuestion()))
                .build());
        }
        builder.preqList(preqDtoList);
        builder.keywordTop5(coverLetter.getKeywords());
        builder.softSkills(coverLetter.getAbilities());

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
