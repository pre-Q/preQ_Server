package kr.co.preq.domain.preq.controller;

import kr.co.preq.domain.preq.dto.ChatGptResponseDto;
import kr.co.preq.domain.preq.dto.QuestionRequestDto;
import kr.co.preq.domain.preq.service.ChatGptService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatGptService.askQuestion(requestDto);
    }
}
