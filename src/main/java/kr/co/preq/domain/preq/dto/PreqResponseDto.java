package kr.co.preq.domain.preq.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PreqResponseDto {
    private Long id;
    private String question;

    @Builder
    public PreqResponseDto(Long id, String question) {
        this.id = id;
        this.question = question;
    }
}
