package kr.co.preq.domain.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class ApplicationCreateResponseDto {
    @JsonProperty("id")
    private Long id;

    @Builder
    public ApplicationCreateResponseDto(Long id) {
        this.id = id;
    }
}
