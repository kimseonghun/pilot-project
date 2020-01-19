package com.woowabros.pilotproject.config.resolver;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SessionMember {
    private Long memberId;
    private String memberName;

    @Builder
    public SessionMember(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
