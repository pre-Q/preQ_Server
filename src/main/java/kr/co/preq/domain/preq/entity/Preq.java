package kr.co.preq.domain.preq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

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
	private CoverLetter coverLetter;

	@Column(nullable = false)
	private String question;

	@Builder
	Preq(CoverLetter coverLetter, String question) {
		Assert.notNull(coverLetter, "coverLetter must not be null");
		Assert.notNull(question, "question must not be null");

		this.coverLetter = coverLetter;
		this.question = question;
	}
}
