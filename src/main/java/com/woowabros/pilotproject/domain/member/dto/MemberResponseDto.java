package com.woowabros.pilotproject.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String memberName;

    @Builder
    public MemberResponseDto(Long id, String memberName) {
        this.id = id;
        this.memberName = memberName;
    }
}
