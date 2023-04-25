package kr.co.preq.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	// public static User createMember(String name, String idCardImageUrl, String email, String password,
	// 	Authority authority) {
	// 	Member member = new Member();
	// 	member.name = name;
	// 	member.idCardImageUrl = idCardImageUrl;
	// 	member.email = email;
	// 	member.password = password;
	// 	member.permission = Permission.WAIT.getStatus();
	// 	member.authority = authority;
	// 	return member;
	// }
}
