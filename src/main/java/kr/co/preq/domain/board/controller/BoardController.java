package kr.co.preq.domain.board.controller;

import kr.co.preq.domain.board.dto.BoardCreateRequestDto;
import kr.co.preq.domain.board.dto.BoardResponseDto;
import kr.co.preq.domain.board.service.BoardService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardResponseDto> createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardResponseDto response = boardService.createBoard(boardCreateRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_POST_SUCCESS, response);

    }
}
