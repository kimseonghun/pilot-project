package com.woowabros.pilotproject.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberResponseDto {
    private Long id;
    private String memberName;

    @Builder
    public MemberResponseDto(Long id, String memberName) {
        this.id = id;
        this.memberName = memberName;
    }
}
