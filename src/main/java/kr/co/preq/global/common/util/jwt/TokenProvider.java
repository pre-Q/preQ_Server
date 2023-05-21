package kr.co.preq.global.common.util.jwt;

import java.security.Key;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// import kr.co.preq.global.common.util.RedisUtil;
import kr.co.preq.global.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {
	private final Key key;
	private final UserDetailsService userDetailsService;
	private final RedisUtil redisUtil;

	public TokenProvider(UserDetailsService userDetailsService, @Value("${jwt.secret}") String secret, RedisUtil redisUtil) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.userDetailsService = userDetailsService;
		this.redisUtil = redisUtil;
	}

	public TokenDto generateTokenDto(Authentication authentication) {

		long now = (new Date()).getTime();
		Date accessTokenExpiration = new Date(now + TokenInfo.ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.setExpiration(accessTokenExpiration)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		Date refreshTokenExpiration = new Date(now + TokenInfo.REFRESH_TOKEN_EXPIRE_TIME);
		String refreshToken = Jwts.builder()
			.setSubject(authentication.getName())
			.setExpiration(refreshTokenExpiration)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return TokenDto.of(TokenInfo.BEARER_TYPE, accessToken, refreshToken);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

		if (claims.getSubject() == null) {
			throw new IllegalArgumentException("권한 정보가 없는 토큰입니다.");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException exception) {
			log.info("잘못된 JWT 서명을 가진 토큰입니다.");
		} catch (ExpiredJwtException exception) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException exception) {
			log.info("지원하지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException exception) {
			log.info("잘못된 JWT 토큰입니다.");
		}
		return false;
	}

	public boolean isLogout(String accessToken) {
		String data = redisUtil.getData(RedisUtil.PREFIX_LOGOUT + accessToken);
		return data != null;
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
