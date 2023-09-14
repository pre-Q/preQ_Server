package kr.co.preq.domain.preq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;
import kr.co.preq.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preq")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preq extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private ApplicationChild applicationChild;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String question;

	@Builder
	public Preq(ApplicationChild applicationChild, String question) {
		Assert.notNull(applicationChild, "coverLetter must not be null");
		Assert.notNull(question, "question must not be null");

		this.applicationChild = applicationChild;
		this.question = question;
	}
}
