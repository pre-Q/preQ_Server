package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.dto.BoardGetAllResponseDto;
import kr.co.preq.domain.board.dto.BoardGetResponseDto;
import kr.co.preq.domain.board.dto.BoardRequestDto;
import kr.co.preq.domain.board.dto.BoardCreateResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import kr.co.preq.domain.comment.dto.CommentResponseDto;
import kr.co.preq.domain.comment.entity.Comment;
import kr.co.preq.domain.comment.repository.CommentRepository;
import kr.co.preq.domain.member.entity.Member;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.global.common.util.exception.CustomException;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    public BoardCreateResponseDto createBoard(BoardRequestDto request) {
        Member member = memberService.findMember();

        String title = request.getTitle();
        String content = request.getContent();

        Board board = new Board(member, title, content);
        boardRepository.save(board);

        return BoardCreateResponseDto.builder()
                .id(board.getId()).build();
    }

    public void updateBoard(Long boardId, BoardRequestDto request) {
        Member member = memberService.findMember();

        Board board = getBoard(boardId);

        board.updateBoard(member, request.getTitle(), request.getContent());

    }

    public List<BoardGetAllResponseDto> getAllBoard(Long filter) {
        List<BoardGetAllResponseDto> results = new ArrayList<>();

        if (filter == 0) {
            List<Board> orderByDate = boardRepository.findAll(Sort.by(desc("createdAt")));

            orderByDate.forEach(board -> {
                results.add(BoardGetAllResponseDto.of(board.getId(), board.getMember().getName(), LocalDateTime.parse(String.valueOf(board.getCreatedAt())), board.getViews(), board.getTitle()));
            });
        }
        else if (filter == 1) {
            List<Board> orderByViews = boardRepository.findAll(Sort.by(desc("views")));

            orderByViews.forEach(board -> {
                results.add(BoardGetAllResponseDto.of(board.getId(), board.getMember().getName(), LocalDateTime.parse(String.valueOf(board.getCreatedAt())), board.getViews(), board.getTitle()));
            });
        }
        else throw new CustomException(ErrorCode.VIOLATE_FILTER_RULE);

        return results;
    }

    public BoardGetResponseDto getDetailBoard(Long boardId) {
        Board board = getBoard(boardId);

        // 조회수 1 증가
        Integer views = board.getViews() + 1;
        board.updateViews(views);

        List<CommentResponseDto> results = new ArrayList<>();
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        comments.forEach(comment -> {
            results.add(CommentResponseDto.of(comment.getId(), comment.getMember().getName(), comment.getContent()));
        });

        return BoardGetResponseDto.builder()
                .id(board.getId())
                .name(board.getMember().getName())
                .createdAt(board.getCreatedAt())
                .views((board.getViews()))
                .title(board.getTitle())
                .content(board.getContent())
                .comments(results)
                .build();
    }

    private Board getBoard(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }
}
