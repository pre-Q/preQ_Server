package kr.co.preq.global.common.util.response;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* 400 BAD REQUEST */
	NO_ID(BAD_REQUEST, "존재하지 않는 id 입니다"),
	BAD_PARAMETER(BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
	VIOLATE_BOARD_RULE(BAD_REQUEST, "제목 또는 본문이 없거나 제목은 최대 100자여야 합니다."),
	VIOLATE_FILTER_RULE(BAD_REQUEST, "필터 범위를 벗어났습니다."),
	NOT_AUTHORIZED(BAD_REQUEST, "허용된 요청이 아닙니다."),
	SEARCH_BOARD_FAIL(BAD_REQUEST, "키워드를 넣어주세요."),
	ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 값입니다"),

	/* 401 UNAUTHORIZED: 인증 실패 */
	UNAUTHORIZED_USER(UNAUTHORIZED, "만료되었거나 잘못된 토큰입니다. 토큰을 확인해주세요."),
	KAKAO_UNAUTHORIZED_USER(UNAUTHORIZED, "카카오 로그인 실패. 만료되었거나 잘못된 카카오 토큰입니다. 카카오 토큰을 확인해주세요."),
	GOOGLE_UNAUTHORIZED_USER(UNAUTHORIZED, "구글 로그인 실패. 만료되었거나 잘못된 구글 토큰입니다. 구글 토큰을 확인해주세요."),

	/* 404 NOT_FOUND: 리소스를 찾을 수 없음 */
	DATA_NOT_FOUND(NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
	USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
	TOKEN_NOT_FOUND(NOT_FOUND, "다시 로그인해주세요."),
	PROFILE_NOT_FOUND(NOT_FOUND, "유저의 프로필 정보가 존재하지 않습니다."),
	PREQ_NOT_FOUND(NOT_FOUND, "preQ를 찾을 수 없습니다."),
	BOARD_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다."),
	COMMENT_NOT_FOUND(NOT_FOUND, "댓글을 찾을 수 없습니다."),

	/* 500 INTERNAL_SERVER_ERROR : 서버 오류 */
	FILE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
	SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류로 인해 응답을 제공할 수 없습니다.");

	private final HttpStatus status;
	private final String message;
}
