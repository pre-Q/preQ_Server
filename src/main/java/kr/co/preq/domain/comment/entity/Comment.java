package kr.co.preq.domain.comment.entity;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.member.entity.Users;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users; // 댓글 작성자

    @Builder
    public Comment(String content, Users users, Board board) {
        this.content = content;
        this.users = users;
        this.board = board;
    }

}
