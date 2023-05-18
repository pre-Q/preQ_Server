package kr.co.preq.domain.board.controller;

import kr.co.preq.domain.board.dto.BoardGetResponseDto;
import kr.co.preq.domain.board.dto.BoardRequestDto;
import kr.co.preq.domain.board.dto.BoardCreateResponseDto;
import kr.co.preq.domain.board.service.BoardService;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.ErrorCode;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardCreateResponseDto> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.error(ErrorCode.VIOLATE_BOARD_RULE);
        }

        BoardCreateResponseDto response = boardService.createBoard(boardRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_POST_SUCCESS, response);
    }

    @PatchMapping("/{boardId}")
    public ApiResponse<Object> patchBoard(@PathVariable Long boardId, @Valid @RequestBody BoardRequestDto boardRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.error(ErrorCode.VIOLATE_BOARD_RULE);
        }

        boardService.updateBoard(boardId, boardRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_UPDATE_SUCCESS);
    }

    @GetMapping("/list")
    public ApiResponse<List<BoardGetResponseDto>> getAllBoard(@RequestParam Long filter) {

        List<BoardGetResponseDto> response =  boardService.getAllBoard(filter);

        return ApiResponse.success(SuccessCode.GET_ALL_BOARD_SUCCESS, response);
    }
}
