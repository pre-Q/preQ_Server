package kr.co.preq.domain.preq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
public class OpenAIRequestDto implements Serializable {
    private String model;
    private Integer n;
    private Double temperature;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("top_p")
    private Double topP;
    private List<Message> messages;

    @Builder
    public OpenAIRequestDto(String model, Integer n, String command, String prompt) {
        this.model = model;
        this.n = n;
        //this.prompt = prompt;
        //this.maxTokens = maxTokens;
        //this.temperature = temperature;
        //this.topP = topP;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", command));
        this.messages.add(new Message("user", prompt));
    }

}
