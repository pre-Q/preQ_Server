
package kr.co.preq.domain.member.entity;

import javax.persistence.*;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.comment.entity.Comment;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)  // 유저 삭제하면 게시글도 자동 삭제
	private List<Board> boardList;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;

	// public static User createMember(String name, String idCardImageUrl, String email, String password,
	// 	Authority authority) {
	// 	Member member = new Member();제
	// 	member.name = name;
	// 	member.idCardImageUrl = idCardImageUrl;
	// 	member.email = email;
	// 	member.password = password;
	// 	member.permission = Permission.WAIT.getStatus();
	// 	member.authority = authority;
	// 	return member;
	// }
}