package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.dto.BoardCreateRequestDto;
import kr.co.preq.domain.board.dto.BoardResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import kr.co.preq.domain.member.entity.Member;

import kr.co.preq.domain.member.service.MemberService;
import kr.co.preq.global.common.util.exception.CustomException;
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

    public BoardResponseDto createBoard(BoardCreateRequestDto request) {
        Member member = memberService.findMember();

        String title = request.getTitle();
        String content = request.getContent();

        Board board = new Board(member, title, content);
        boardRepository.save(board);

        return BoardResponseDto.builder()
                .id(board.getId()).build();
    }
}
