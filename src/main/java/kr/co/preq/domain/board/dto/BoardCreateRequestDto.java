package kr.co.preq.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    private String content;
}
