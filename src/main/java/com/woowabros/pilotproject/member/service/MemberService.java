package com.woowabros.pilotproject.member.service;

import com.woowabros.pilotproject.member.domain.Member;
import com.woowabros.pilotproject.member.domain.MemberRepository;
import com.woowabros.pilotproject.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.member.exception.LoginFailException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponseDto save(Member member) {
        Member newMember = memberRepository.save(member);
        return MemberResponseDto.builder()
                .id(newMember.getId())
                .memberName(newMember.getMemberName())
                .build();
    }

    public Member login(Member loginMember) {
        Member member = memberRepository.findByMemberName(loginMember.getMemberName())
                .orElseThrow(LoginFailException::new);

        if (member.isWrongPassword(loginMember.getPassword())) {
            throw new LoginFailException();
        }

        return member;
    }
}
