package kr.co.preq.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class BoardGetResponseDto {
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    private Integer views;

    private String title;

    public static BoardGetResponseDto of(Long id, String name, LocalDateTime createdAt, Integer views, String title) {
        return new BoardGetResponseDto(id, name, createdAt, views, title);
    }
}
