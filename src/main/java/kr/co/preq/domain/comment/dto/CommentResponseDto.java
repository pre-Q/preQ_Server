package kr.co.preq.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String name;
    private String content;

    public static CommentResponseDto of(Long id, String name, String content) {
        return new CommentResponseDto(id, name, content);
    }
}
