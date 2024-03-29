package kr.co.preq.domain.preq.dto.response;

import kr.co.preq.domain.preq.dto.Choice;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OpenAIResponseDto {
    private String question;
    private String id;
    private String object;
    private String model;
    private List<Choice> choices;

    @Builder
    public OpenAIResponseDto(String id, String object, String model, List<Choice> choices) {
        this.id = id;
        this.object = object;
        this.model = model;
        this.choices = choices;
    }
}
