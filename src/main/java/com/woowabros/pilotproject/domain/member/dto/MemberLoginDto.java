package com.woowabros.pilotproject.domain.member.dto;

import com.woowabros.pilotproject.domain.member.domain.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberLoginDto {

    private String memberName;
    private String password;

    @Builder
    public MemberLoginDto(String memberName, String password) {
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
