package kr.co.preq.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.Assert;

import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    Member(String name, String email) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(email, "email must not be null");

        this.name = name;
        this.email = email;
    }
}

