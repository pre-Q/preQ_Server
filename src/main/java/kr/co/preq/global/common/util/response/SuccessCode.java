package kr.co.preq.global.common.util.response;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	/* 200 OK */
	GET_SUCCESS(OK, "조회 성공"),

	// 인증
	SIGNUP_SUCCESS(OK, "회원가입 성공"),
	LOGIN_SUCCESS(OK, "로그인 성공"),
	LOGOUT_SUCCESS(OK, "로그아웃 성공"),
	ACCOUNT_READ_SUCCESS(OK, "계정 조회 성공"),
	TOKEN_REISSUE_SUCCESS(OK, "토큰 재발급 성공"),
	DELETE_ACCOUNT_SUCCESS(OK, "회원 탈퇴 성공"),

	// 게시판
	BOARD_POST_SUCCESS(CREATED, "글 작성 성공"),
	BOARD_UPDATE_SUCCESS(OK, "글 수정 성공"),
	GET_ALL_BOARD_SUCCESS(OK, "게시판 전체 조회 성공"),
	GET_DETAIL_BOARD_SUCCESS(OK, "글 상세조회 성공"),
	BOARD_DELETE_SUCCESS(OK, "글 삭제 성공"),
	SEARCH_BOARD_SUCCESS(OK, "게시판 글 검색 성공"),

	// 댓글
	COMMENT_CREATE_SUCCESS(CREATED, "댓글 생성 성공"),
	COMMENT_DELETE_SUCCESS(OK, "댓글 삭제 성공"),

	// 자기소개서
	APPLICATION_CHILD_CREATE_SUCCESS(CREATED, "지원서 문항 생성 성공"),
	PREQ_GET_SUCCESS(OK, "예상 면접 질문 조회 성공"),

	// 지원서
	APPLICATION_CREATE_SUCCESS(CREATED, "지원서 생성 성공"),
	APPLICATION_LIST_GET_SUCCESS(OK, "지원서 리스트 조회 성공"),
	APPLICATION_GET_SUCCESS(OK, "지원서 상세 조회 성공"),
	APPLICATION_TITLE_UPDATE_SUCCESS(OK, "지원서 제목 수정 성공"),
	APPLICATION_MEMO_UPDATE_SUCCESS(OK, "지원서 메모 수정 성공"),
	APPLICATION_DELETE_SUCCESS(OK, "지원서 삭제 성공");

	private final HttpStatus status;
	private final String message;
}
