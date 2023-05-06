package kr.co.preq.domain.preq.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Choice {
    private Integer index;
    private Message message;

    @Builder
    public Choice(Integer index, Message message) {
        this.index = index;
        this.message = message;
    }
}
