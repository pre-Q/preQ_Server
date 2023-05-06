package kr.co.preq.domain.preq.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PreqResponseDto {
    private String id;
    private String question;

    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    @Builder
    public PreqResponseDto(String id, String object, LocalDate created, String model,
                           List<Choice> choices) {
        this.id = id;
        //this.question = question;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }
}
