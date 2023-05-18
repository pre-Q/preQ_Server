package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.dto.BoardGetResponseDto;
import kr.co.preq.domain.board.dto.BoardRequestDto;
import kr.co.preq.domain.board.dto.BoardCreateResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
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

    public List<BoardGetResponseDto> getAllBoard(Long filter) {
        List<BoardGetResponseDto> results = new ArrayList<>();

        if (filter == 0) {
            List<Board> orderByDate = boardRepository.findAll(Sort.by(desc("createdAt")));

            orderByDate.forEach(board -> {
                results.add(BoardGetResponseDto.of(board.getId(), board.getMember().getName(), LocalDateTime.parse(String.valueOf(board.getCreatedAt())), board.getViews(), board.getTitle()));
            });
        }
        else if (filter == 1) {
            List<Board> orderByViews = boardRepository.findAll(Sort.by(desc("views")));

            orderByViews.forEach(board -> {
                results.add(BoardGetResponseDto.of(board.getId(), board.getMember().getName(), LocalDateTime.parse(String.valueOf(board.getCreatedAt())), board.getViews(), board.getTitle()));
            });
        }
        else throw new CustomException(ErrorCode.VIOLATE_FILTER_RULE);

        return results;
    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        return board;
    }
}
