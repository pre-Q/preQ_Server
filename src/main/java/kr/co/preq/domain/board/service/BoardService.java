package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.dto.response.BoardGetAllResponseDto;
import kr.co.preq.domain.board.dto.response.BoardGetResponseDto;
import kr.co.preq.domain.board.dto.request.BoardRequestDto;
import kr.co.preq.domain.board.dto.response.BoardCreateResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import kr.co.preq.domain.comment.dto.response.CommentResponseDto;
import kr.co.preq.domain.comment.entity.Comment;
import kr.co.preq.domain.comment.repository.CommentRepository;
import kr.co.preq.domain.member.entity.Member;

import kr.co.preq.domain.member.repository.MemberRepository;
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
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public BoardCreateResponseDto createBoard(Long memberId, BoardRequestDto request) {
        Member member = findMember(memberId);

        String title = request.getTitle();
        String content = request.getContent();

        Board board = new Board(member, title, content);
        boardRepository.save(board);

        return BoardCreateResponseDto.builder()
                .id(board.getId()).build();
    }

    public void updateBoard(Long memberId, Long boardId, BoardRequestDto request) {
        Member member = findMember(memberId);

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

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        boardRepository.delete(board);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    public List<BoardGetAllResponseDto> getSearchedBoard(String keyword) {
        List<BoardGetAllResponseDto> results = new ArrayList<>();

        List<Board> boardsBySearch = boardRepository.findByTitleContaining(keyword);
        boardsBySearch.forEach(board -> {
                results.add(BoardGetAllResponseDto.of(board.getId(), board.getMember().getName(), LocalDateTime.parse(String.valueOf(board.getCreatedAt())), board.getViews(), board.getTitle()));
        });

        return results;
    }
}
