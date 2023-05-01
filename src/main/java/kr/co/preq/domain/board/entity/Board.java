package kr.co.preq.domain.board.entity;

import kr.co.preq.domain.comment.entity.Comment;
import kr.co.preq.domain.user.entity.Member;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Integer views;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)  // 게시글 삭제하면 해당 댓글도 자동 삭제
    private List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;   // 게시글 작성자

    @Builder
    public Board(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

}
