package com.woowabros.pilotproject.domain.menu.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    @Test
    void 메뉴_생성_테스트() {
        // given
        Menu menu = Menu.builder()
                .name("치킨")
                .price(20000)
                .build();

        // when & then
        assertThat(menu).isEqualTo(Menu.builder()
                .name("치킨")
                .price(20000)
                .build());
    }
}