package kr.co.preq.domain.application.entity;

import kr.co.preq.domain.applicationChild.entity.StringListConverter;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Application extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String title;

    @Column
    private String memo;

    @ManyToOne
    private Member member;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> keywords;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> abilities;

    @Builder
    public Application(String title, String memo, Member member) {
        this.title = title;
        this.memo = memo;
        this.member = member;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void updateAbilities(List<String> abilities) {
        this.abilities = abilities;
    }
}
