package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date registrableDate;

    @Builder
    public Coupon(Date registrableDate) {
        this.registrableDate = registrableDate;
    }

    public boolean isPossibleToRegister() {
        return new Date().before(registrableDate);
    }
}
