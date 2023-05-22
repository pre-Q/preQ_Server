package kr.co.preq.domain.preq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class PreqResponseDto {
    private Long id;
    private String question;
}
