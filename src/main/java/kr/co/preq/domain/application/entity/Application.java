package kr.co.preq.domain.application.entity;

import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Application extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String title;

    @Column
    private String memo;

    @ManyToOne
    private Member member;

    @Builder
    public Application(String title, String memo, Member member) {
        this.title = title;
        this.memo = memo;
        this.member = member;
    }
}
