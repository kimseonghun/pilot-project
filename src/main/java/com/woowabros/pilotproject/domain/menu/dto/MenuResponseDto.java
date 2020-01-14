package com.woowabros.pilotproject.domain.menu.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MenuResponseDto {
    private String menuName;
    private Integer menuPrice;

    @Builder
    public MenuResponseDto(String menuName, Integer menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }
}
