package kr.co.preq.domain.preq;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";
	// @Value("${openai.api.key}") public static String API_KEY;
	public static final String MODEL = "gpt-3.5-turbo";
	public static final Integer MAX_TOKEN = 300;
	public static final Double TEMPERATURE = 0.0;
	public static final Double TOP_P = 1.0;
	public static final Integer N = 3;
	public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
	public static final String URL = "https://api.openai.com/v1/chat/completions";
}