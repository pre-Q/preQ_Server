package kr.co.preq.domain.user.service;

import kr.co.preq.domain.user.entity.Member;
import kr.co.preq.domain.user.repository.MemberRepository;
import kr.co.preq.global.common.util.exception.NotFoundException;
import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional()
    public Member findMember() {
        return memberRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
