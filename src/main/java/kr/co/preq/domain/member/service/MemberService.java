package kr.co.preq.domain.member.service;

import kr.co.preq.domain.auth.service.AuthService;
import kr.co.preq.domain.member.dto.response.MemberResponseDto;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @Transactional
    public MemberResponseDto getProfile() {
        Member member = authService.findMember();
        return MemberResponseDto.builder()
            .email(member.getEmail())
            .name(member.getName())
            .build();
    }

    @Transactional
    public MemberResponseDto deleteProfile() {
        Member member = authService.findMember();
        MemberResponseDto responseDto = MemberResponseDto.builder()
            .email(member.getEmail())
            .name(member.getName())
            .build();

        memberRepository.delete(member);

        return responseDto;
    }

}