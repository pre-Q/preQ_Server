package kr.co.preq.domain.board.controller;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public String postBoard(Board board) {
        boardService.write(board);
        return "/board";
    }
}
