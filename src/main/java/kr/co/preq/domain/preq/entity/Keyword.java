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
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private CoverLetter coverLetter;

	@Column(nullable = false)
	private String keyword;

	@Column(nullable = false)
	private String percentage;

	@Builder
	Keyword(CoverLetter coverLetter, String keyword, String percentage) {
		Assert.notNull(coverLetter, "coverLetter must not be null");
		Assert.notNull(keyword, "keyword must not be null");
		Assert.notNull(percentage, "percentage must not be null");

		this.coverLetter = coverLetter;
		this.keyword = keyword;
		this.percentage = percentage;
	}
}
