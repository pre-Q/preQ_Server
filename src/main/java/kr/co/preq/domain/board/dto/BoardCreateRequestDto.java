package kr.co.preq.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
