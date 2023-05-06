package kr.co.preq.domain.preq.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreqResult {
    private Long id;
    private String question;

    @Builder
    public PreqResult(Long id, String question) {
        this.id = id;
        this.question = question;
    }
}
