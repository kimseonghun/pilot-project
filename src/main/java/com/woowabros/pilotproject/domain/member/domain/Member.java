package com.woowabros.pilotproject.domain.member.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberName;

    @Column(nullable = false)
    private String password;

    @Builder
    public Member(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }

    public boolean isWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
