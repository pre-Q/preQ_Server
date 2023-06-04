package kr.co.preq.domain.board.controller;

import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.board.dto.BoardGetAllResponseDto;
import kr.co.preq.domain.board.dto.BoardGetResponseDto;
import kr.co.preq.domain.board.dto.BoardRequestDto;
import kr.co.preq.domain.board.dto.BoardCreateResponseDto;
import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.service.BoardService;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.util.response.ApiResponse;
import kr.co.preq.global.common.util.response.ErrorCode;
import kr.co.preq.global.common.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;
    private final AuthService authService;

    @PostMapping
    public ApiResponse<BoardCreateResponseDto> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.error(ErrorCode.VIOLATE_BOARD_RULE);
        }

        Member member = authService.findMember();
        Long memberId = member.getId();

        BoardCreateResponseDto response = boardService.createBoard(memberId, boardRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_POST_SUCCESS, response);
    }

    @PatchMapping("/{boardId}")
    public ApiResponse<Object> patchBoard(@PathVariable Long boardId, @Valid @RequestBody BoardRequestDto boardRequestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.error(ErrorCode.VIOLATE_BOARD_RULE);
        }

        Member member = authService.findMember();
        Long memberId = member.getId();

        boardService.updateBoard(memberId, boardId, boardRequestDto);

        return ApiResponse.success(SuccessCode.BOARD_UPDATE_SUCCESS);
    }

    @GetMapping("/list")
    public ApiResponse<List<BoardGetAllResponseDto>> getAllBoard(@RequestParam Long filter) {

        List<BoardGetAllResponseDto> response =  boardService.getAllBoard(filter);

        return ApiResponse.success(SuccessCode.GET_ALL_BOARD_SUCCESS, response);
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardGetResponseDto> getDetailBoard(@PathVariable Long boardId) {

        BoardGetResponseDto response = boardService.getDetailBoard(boardId);

        return ApiResponse.success(SuccessCode.GET_DETAIL_BOARD_SUCCESS, response);
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<Object> deleteBoard(@PathVariable Long boardId) {

        boardService.deleteBoard(boardId);

        return ApiResponse.success(SuccessCode.BOARD_DELETE_SUCCESS);
    }

    @GetMapping("/search")
    public ApiResponse<List<BoardGetAllResponseDto>> getSearchedBoard(@RequestParam @NotBlank String keyword) {

        if (keyword == null) {
            return ApiResponse.error(ErrorCode.SEARCH_BOARD_FAIL);
        }

        List<BoardGetAllResponseDto> response = boardService.getSearchedBoard(keyword);

        return ApiResponse.success(SuccessCode.SEARCH_BOARD_SUCCESS, response);
    }
}
