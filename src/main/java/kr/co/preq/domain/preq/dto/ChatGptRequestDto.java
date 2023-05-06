package kr.co.preq.domain.preq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatGptRequestDto implements Serializable {
    private String model;
    //private String prompt;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Integer n;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;
    private List<Message> messages;

    @Builder
    public ChatGptRequestDto(String model, Integer n, String prompt) {
        this.model = model;
        this.n = n;
        //this.prompt = prompt;
        //this.maxTokens = maxTokens;
        //this.temperature = temperature;
        //this.topP = topP;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

}
