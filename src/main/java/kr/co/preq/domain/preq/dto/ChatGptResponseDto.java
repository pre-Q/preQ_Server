package kr.co.preq.domain.preq.dto;

import lombok.*;

import java.io.Serializable;
import java.net.ContentHandler;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatGptResponseDto implements Serializable {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    @Builder
    public ChatGptResponseDto(String id, String object,
                              LocalDate created, String model,
                              List<Choice> choices) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }

}
