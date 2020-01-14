package com.woowabros.pilotproject.domain.menu.service;

import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.domain.MenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Test
    void 메뉴_저장_테스트() {
        // given
        Menu menu = mock(Menu.class);
        given(menuRepository.save(any())).willReturn(menu);

        // when
        Menu result = menuService.save(menu);

        // then
        assertThat(result).isEqualTo(menu);
        verify(menuRepository, times(1)).save(any());
    }

    @Test
    void 메뉴_전체_조회_테스트() {
        // given
        Menu menu = mock(Menu.class);
        List<Menu> menus = Collections.singletonList(menu);
        given(menuRepository.findAll()).willReturn(menus);

        // when & then
        assertThat(menuService.findAll())
                .contains(menu)
                .isEqualTo(menus);
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void 메뉴_단건_조회_테스트() {
        // given
        Menu menu = mock(Menu.class);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

        // when & then
        assertThat(menuService.findById(1L)).isEqualTo(menu);
        verify(menuRepository, times(1)).findById(anyLong());
    }
}