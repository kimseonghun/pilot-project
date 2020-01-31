package com.woowabros.pilotproject.domain.member.dto;

import com.woowabros.pilotproject.domain.member.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberCreateDto {

    @NotBlank
    private String memberName;

    @NotBlank
    private String password;

    @Builder
    public MemberCreateDto(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .password(password)
                .build();
    }
}
