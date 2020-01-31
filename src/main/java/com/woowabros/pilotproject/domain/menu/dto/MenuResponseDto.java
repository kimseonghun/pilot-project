package com.woowabros.pilotproject.domain.menu.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MenuResponseDto {

    private Long menuId;
    private String menuName;
    private Integer menuPrice;

    @Builder
    public MenuResponseDto(Long menuId, String menuName, Integer menuPrice) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }
}
