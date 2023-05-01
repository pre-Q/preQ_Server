package kr.co.preq.domain.preq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.preq.entity.Preq;

public interface PreqRepository extends JpaRepository<Preq, Long> {
}
