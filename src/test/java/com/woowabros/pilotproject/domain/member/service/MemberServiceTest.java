package com.woowabros.pilotproject.domain.member.service;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.domain.MemberRepository;
import com.woowabros.pilotproject.domain.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.domain.member.exception.LoginFailException;
import com.woowabros.pilotproject.domain.member.exception.NotFoundMemberException;
import org.junit.jupiter.api.BeforeEach;
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

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .memberName("memberName")
                .password("password")
                .build();
    }

    @Test
    void 회원_생성_테스트() {
        // given
        given(memberRepository.save(member)).willReturn(member);

        // when
        MemberResponseDto result = memberService.save(member);

        // then
        assertThat(result).isEqualTo(MemberResponseDto.builder()
                .memberName(member.getMemberName())
                .build());
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    void 회원_로그인_테스트() {
        // given
        given(memberRepository.findByMemberName("memberName")).willReturn(Optional.of(member));

        // when & then
        assertDoesNotThrow(() -> memberService.login(member));
        verify(memberRepository, times(1)).findByMemberName(anyString());
    }

    @Test
    void 없는_회원_이름에_대한_로그인_실패_예외_테스트() {
        // given
        given(memberRepository.findByMemberName("memberName")).willReturn(Optional.empty());

        // when & then
        assertThrows(LoginFailException.class, () -> memberService.login(member));
        verify(memberRepository, times(1)).findByMemberName(anyString());
    }

    @Test
    void 틀린_비밀번호에_대한_로그인_실패_예외_테스트() {
        // given
        Member wrongPasswordMember = Member.builder()
                .memberName("memberName")
                .password("wrong password")
                .build();
        given(memberRepository.findByMemberName("memberName")).willReturn(Optional.of(member));

        // when & then
        assertThrows(LoginFailException.class, () -> memberService.login(wrongPasswordMember));
    }

    @Test
    void 회원_단건_조회_테스트() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when & then
        assertThat(memberService.findById(1L)).isEqualTo(member);
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    void 없는_회원_조회_시_예외_테스트() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundMemberException.class, () -> memberService.findById(1L));
        verify(memberRepository, times(1)).findById(anyLong());
    }
}