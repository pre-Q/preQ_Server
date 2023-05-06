package kr.co.preq.domain.preq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Choice {
    private String text;
    private Integer index;
    private Message message;

    @Builder
    public Choice(String text, Integer index, Message message) {
        this.text = text;
        this.index = index;
        this.message = message;
    }
}
