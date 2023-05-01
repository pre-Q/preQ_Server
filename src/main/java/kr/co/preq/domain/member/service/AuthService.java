package kr.co.preq.domain.member.service;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member findMember() {
		return memberRepository.findByEmail("preq@gmail.com")		//SecurityUtil.getCurrentUserEmail()
			.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}
}
