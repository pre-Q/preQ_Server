package kr.co.preq.domain.comment.service;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import kr.co.preq.domain.comment.dto.CommentCreateResponseDto;
import kr.co.preq.domain.comment.dto.CommentRequestDto;
import kr.co.preq.domain.comment.entity.Comment;
import kr.co.preq.domain.comment.repository.CommentRepository;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentCreateResponseDto createComment(Long memberId, Long boardId, CommentRequestDto request) {
        Member member = findMember(memberId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        String content = request.getContent();

        Comment comment = new Comment(content, member, board);

        commentRepository.save(comment);

        return CommentCreateResponseDto.builder()
                .commentId(comment.getId())
                .name(comment.getMember().getName())
                .build();
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
