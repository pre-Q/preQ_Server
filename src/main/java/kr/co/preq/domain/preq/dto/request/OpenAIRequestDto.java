package kr.co.preq.domain.preq.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.preq.domain.preq.dto.Message;
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
    public OpenAIRequestDto(String model, Integer n, String command, List<Message> sessions, String prompt) {
        this.model = model;
        this.n = n;
        //this.prompt = prompt;
        //this.maxTokens = maxTokens;
        //this.temperature = temperature;
        //this.topP = topP;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", command));
        this.messages.addAll(sessions);
        this.messages.add(new Message("user", prompt));
    }
}
