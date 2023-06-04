package kr.co.preq.domain.preq.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.preq.domain.preq.entity.Preq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverLetterResponseDto {
	private Long id;
	private String question;
}
