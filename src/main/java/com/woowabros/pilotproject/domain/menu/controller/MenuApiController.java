package com.woowabros.pilotproject.domain.menu.controller;

import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.menu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuApiController {
    private final MenuService menuService;

    public MenuApiController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> findAll() {
        List<MenuResponseDto> response = menuService.findAll();
        return ResponseEntity.ok(response);
    }
}
