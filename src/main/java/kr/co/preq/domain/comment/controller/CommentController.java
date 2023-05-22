package kr.co.preq.domain.comment.controller;

import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.comment.dto.CommentCreateResponseDto;
import kr.co.preq.domain.comment.dto.CommentRequestDto;
import kr.co.preq.domain.comment.service.CommentService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping("/comment/{boardId}")
    public ApiResponse<CommentCreateResponseDto> createComment(@PathVariable Long boardId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        Member member = authService.findMember();
        Long memberId = member.getId();

        CommentCreateResponseDto response = commentService.createComment(memberId, boardId, commentRequestDto);

        return ApiResponse.success(SuccessCode.COMMENT_CREATE_SUCCESS, response);
    }
}
