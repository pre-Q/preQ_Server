package kr.co.preq.domain.preq.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionDto {
	private String question;
	private String answer;
	private List<String> preqList;
}
