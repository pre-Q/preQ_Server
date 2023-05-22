package kr.co.preq.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreateResponseDto {
    private Long commentId;
    private String name;
}
