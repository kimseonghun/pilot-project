package com.woowabros.pilotproject.domain.menu.service;

import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.domain.MenuRepository;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.menu.exception.NotFoundMenuException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<MenuResponseDto> findAll() {
        return menuRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Menu findById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(NotFoundMenuException::new);
    }

    private MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .menuId(menu.getId())
                .menuName(menu.getName())
                .menuPrice(menu.getPrice())
                .build();
    }
}
