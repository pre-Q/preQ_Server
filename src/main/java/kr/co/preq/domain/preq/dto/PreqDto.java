package kr.co.preq.domain.preq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PreqDto {
    private Long id;
    private String question;
}
