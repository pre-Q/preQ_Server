package kr.co.preq.domain.board.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotNull
    private String title;
    @NotNull
    private String content;
}
