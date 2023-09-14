package kr.co.preq.domain.preq.dto;


import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreqMapper {

    public PreqAndKeywordResponseDto toResponseDto(List<String> questions, KeywordAndSoftskillsResponseDto keywordInfo) {
        if (questions == null) return null;

        PreqAndKeywordResponseDto.PreqAndKeywordResponseDtoBuilder builder = PreqAndKeywordResponseDto.builder();
        builder.preqList(questions);
        builder.keywordTop5(keywordInfo.getData().getKeywordTop5());
        builder.softSkills(keywordInfo.getData().getSoftSkills());

        return builder.build();
    }
}
