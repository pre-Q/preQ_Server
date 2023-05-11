package kr.co.preq.global.common.util.jwt;

public class TokenInfo {
	private TokenInfo() {
	}

	public static final String AUTHORITIES_KEY = "AUTH";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_TYPE = "Bearer";
	public static final int START_TOKEN_LOCATION = 7;
	public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30L;	// 1달
	public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 60L;  // 2달
}
