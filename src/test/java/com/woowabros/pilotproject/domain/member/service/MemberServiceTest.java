package com.woowabros.pilotproject.domain.member.service;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.domain.MemberRepository;
import com.woowabros.pilotproject.domain.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.domain.member.exception.LoginFailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 회원_생성_테스트() {
        // given
        Member member = mock(Member.class);
        given(memberRepository.save(member)).willReturn(member);

        // when
        MemberResponseDto result = memberService.save(member);

        // then
        assertThat(result.getMemberName()).isEqualTo(member.getMemberName());
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    void 회원_로그인_테스트() {
        // given
        Member member = Member.builder()
                .memberName("memberName")
                .password("password")
                .build();
        given(memberRepository.findByMemberName("memberName")).willReturn(Optional.of(member));

        // when & then
        assertDoesNotThrow(() -> memberService.login(member));
        verify(memberRepository, times(1)).findByMemberName(anyString());
    }

    @Test
    void 회원_로그인_실패_예외처리() {
        // given
        Member member = Member.builder()
                .memberName("memberName")
                .password("password")
                .build();
        given(memberRepository.findByMemberName("memberName")).willReturn(Optional.empty());

        // when & then
        assertThrows(LoginFailException.class, () -> memberService.login(member));
        verify(memberRepository, times(1)).findByMemberName(anyString());
    }
}