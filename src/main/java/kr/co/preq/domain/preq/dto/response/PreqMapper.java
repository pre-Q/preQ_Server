package kr.co.preq.domain.preq.dto.response;


import java.util.List;

import kr.co.preq.domain.preq.dto.response.KeywordAndSoftskillsResponseDto;
import kr.co.preq.domain.preq.dto.response.PreqAndKeywordResponseDto;
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
