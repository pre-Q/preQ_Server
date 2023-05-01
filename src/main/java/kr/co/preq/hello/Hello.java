package kr.co.preq.hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import kr.co.preq.global.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hello")
@NoArgsConstructor
public class Hello extends BaseEntity {

	@Column
	@Getter
	@Setter
	private String name;

	Hello(String name) {
		this.name = name;
	}

	public static Hello from(String name) {
		return new Hello(name);
	}
}
