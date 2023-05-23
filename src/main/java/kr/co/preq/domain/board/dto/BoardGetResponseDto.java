package kr.co.preq.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.preq.domain.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class BoardGetResponseDto {
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    private Integer views;

    private String title;

    private String content;

    private List<CommentResponseDto> comments;
}
