package com.woowabros.pilotproject.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .memberName("memberName")
                .password("password")
                .build();
    }

    @Test
    void 회원_동등성_테스트() {
        // given
        Member actual = Member.builder()
                .memberName("memberName")
                .password("password")
                .build();

        // when & then
        assertThat(actual).isEqualTo(member);
    }

    @Test
    void 회원_계정_아이디_일치_확인_테스트() {
        // given
        String memberName = "memberName";

        // when & then
        assertThat(member.isSameName(memberName)).isTrue();
    }

    @Test
    void 회원_비밀번호_일치_확인_테스트() {
        // given
        String password = "password";

        // when & then
        assertThat(member.isWrongPassword(password)).isFalse();
    }
}