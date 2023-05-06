package kr.co.preq.domain.preq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String role;
    private String content;

    public Message(String user, String prompt) {
        this.role = user;
        this.content = prompt;
    }
}
