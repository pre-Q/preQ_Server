package kr.co.preq.domain.preq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

import kr.co.preq.domain.user.entity.Member;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coverletter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetter extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private String question;

	@Column(nullable = false, unique = true)
	private String answer;

	@Builder
	CoverLetter(Member member, String question, String answer) {
		Assert.notNull(member, "member must not be null");
		Assert.notNull(question, "question must not be null");
		Assert.notNull(answer, "answer must not be null");

		this.member = member;
		this.question = question;
		this.answer = answer;
	}

}
