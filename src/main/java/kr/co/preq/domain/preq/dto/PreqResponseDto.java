package kr.co.preq.domain.preq.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PreqResponseDto {
    private String question;
    private String id;

    private String object;

    private String model;
    private List<Choice> choices;

    @Builder
    public PreqResponseDto(String id, String object, String model,
                           List<Choice> choices) {
        this.id = id;
        this.object = object;
        this.model = model;
        this.choices = choices;
    }
}
