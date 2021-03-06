package com.woowabros.pilotproject.domain.member.service;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.domain.MemberRepository;
import com.woowabros.pilotproject.domain.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.domain.member.exception.LoginFailException;
import com.woowabros.pilotproject.domain.member.exception.NotFoundMemberException;
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

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
    }
}
