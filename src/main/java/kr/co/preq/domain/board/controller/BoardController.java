package kr.co.preq.domain.board.controller;

import kr.co.preq.domain.board.dto.BoardCreateRequestDto;
import kr.co.preq.domain.board.dto.BoardResponseDto;
import kr.co.preq.domain.board.service.BoardService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.ErrorCode;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardResponseDto> createBoard(@Valid @RequestBody BoardCreateRequestDto boardCreateRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.error(ErrorCode.BOARD_POST_FAIL);
        }

        BoardResponseDto response = boardService.createBoard(boardCreateRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_POST_SUCCESS, response);

    }
}
