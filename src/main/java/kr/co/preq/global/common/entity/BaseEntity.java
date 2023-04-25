package kr.co.preq.global.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

// import kr.co.wingle.common.constants.ErrorCode;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Getter
	private Long id;

	@Column(name = "created_time", updatable = false, nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@CreatedDate
	@Getter
	private LocalDateTime createdTime;

	@Column(name = "is_deleted")
	@Getter
	private boolean isDeleted = false;

	// public boolean softDelete() {
	// 	if (isDeleted == true)
	// 		throw new IllegalStateException(ErrorCode.ALREADY_DELETED.getMessage());
	// 	this.isDeleted = true;
	// 	return true;
	// }
}
