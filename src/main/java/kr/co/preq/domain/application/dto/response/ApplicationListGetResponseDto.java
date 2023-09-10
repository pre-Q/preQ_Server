package kr.co.preq.domain.application.dto.response;

import kr.co.preq.domain.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class ApplicationListGetResponseDto {
    private Long applicationId;
    private String title;
    
    public static ApplicationListGetResponseDto of(Application application) {
        return new ApplicationListGetResponseDto(application.getId(), application.getTitle());
    }
}
