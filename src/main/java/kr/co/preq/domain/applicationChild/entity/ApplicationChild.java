package kr.co.preq.domain.applicationChild.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

import kr.co.preq.domain.application.entity.Application;
import kr.co.preq.domain.member.entity.Member;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applicationChild")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationChild extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Application application;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String answer;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> keywords;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> abilities;

	@Builder
	public ApplicationChild(Application application, Member member, String question, String answer) {
		Assert.notNull(application, "application must not be null");
		Assert.notNull(member, "member must not be null");
		Assert.notNull(question, "question must not be null");
		Assert.notNull(answer, "answer must not be null");

		this.application = application;
		this.member = member;
		this.question = question;
		this.answer = answer;
	}

	public void updateKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void updateAbilities(List<String> abilities) {
		this.abilities = abilities;
	}
}
