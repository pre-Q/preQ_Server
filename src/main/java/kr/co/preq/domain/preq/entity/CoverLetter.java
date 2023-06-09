package kr.co.preq.domain.preq.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

import kr.co.preq.domain.member.entity.Member;
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

	@Column(nullable = false, columnDefinition = "TEXT")
	private String question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String answer;

	@Column(nullable = false)
	@Convert(converter = StringListConverter.class)
	private List<String> keywords;

	@Column(nullable = false)
	@Convert(converter = StringListConverter.class)
	private List<String> abilities;

	@Builder
	CoverLetter(Member member, String question, String answer, List<String> keywords, List<String> abilities) {
		Assert.notNull(member, "member must not be null");
		Assert.notNull(question, "question must not be null");
		Assert.notNull(answer, "answer must not be null");
		Assert.notNull(keywords, "keywords must not be null");
		Assert.notNull(abilities, "abilities must not be null");


		this.member = member;
		this.question = question;
		this.answer = answer;
		this.keywords = keywords;
		this.abilities = abilities;
	}
}
