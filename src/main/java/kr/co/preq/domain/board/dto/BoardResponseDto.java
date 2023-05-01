package kr.co.preq.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardResponseDto {
    @JsonProperty("id")
    private Long id;

    @Builder
    public BoardResponseDto(Long id) {
        this.id = id;
    }
}
