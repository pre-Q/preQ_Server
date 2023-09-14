package kr.co.preq.domain.comment.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank
    @Size(min=1, max=200)
    private String content;
}
