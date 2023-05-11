package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.dto.BoardRequestDto;
import kr.co.preq.domain.board.dto.BoardResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import kr.co.preq.domain.member.entity.Member;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public BoardResponseDto createBoard(BoardRequestDto request) {
        Member member = memberService.findMember();

        String title = request.getTitle();
        String content = request.getContent();

        Board board = new Board(member, title, content);
        boardRepository.save(board);

        return BoardResponseDto.builder()
                .id(board.getId()).build();
    }

    public void updateBoard(Long boardId, BoardRequestDto request) {
        Member member = memberService.findMember();

        Board board = getBoard(boardId);

        board.updateBoard(member, request.getTitle(), request.getContent());

    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        return board;
    }
}
