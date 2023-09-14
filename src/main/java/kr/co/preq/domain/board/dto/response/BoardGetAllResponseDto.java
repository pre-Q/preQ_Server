package kr.co.preq.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class BoardGetAllResponseDto {
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    private Integer views;

    private String title;

    public static BoardGetAllResponseDto of(Long id, String name, LocalDateTime createdAt, Integer views, String title) {
        return new BoardGetAllResponseDto(id, name, createdAt, views, title);
    }
}
