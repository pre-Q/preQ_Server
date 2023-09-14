package kr.co.preq.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardCreateResponseDto {
    @JsonProperty("id")
    private Long id;

    @Builder
    public BoardCreateResponseDto(Long id) {
        this.id = id;
    }
}
