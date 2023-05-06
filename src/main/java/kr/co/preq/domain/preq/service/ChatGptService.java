package kr.co.preq.domain.preq.service;

import kr.co.preq.domain.preq.ChatGptConfig;
import kr.co.preq.domain.preq.dto.ChatGptRequestDto;
import kr.co.preq.domain.preq.dto.ChatGptResponseDto;
import kr.co.preq.domain.preq.dto.QuestionRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ChatGptService {

    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    /*restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
        public boolean hasError(ClientHttpResponse response) throws IOException {
            HttpStatus statusCode = response.getStatusCode();
            return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
        }
    });*/

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }
}
