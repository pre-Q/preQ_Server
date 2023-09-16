package kr.co.preq.domain.application.dto.response;

import kr.co.preq.domain.applicationChild.dto.response.ApplicationChildResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class ApplicationGetResponseDto {
    private Long id;
    private String title;
    private String memo;
    private List<ApplicationChildResponseDto> applicationChild;
    private List<String> keywordTop5;
    private List<String> softSkills;
}
