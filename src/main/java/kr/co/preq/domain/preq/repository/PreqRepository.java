package kr.co.preq.domain.preq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.preq.entity.Preq;

import java.util.List;

public interface PreqRepository extends JpaRepository<Preq, Long> {
    List<Preq> findPreqsByApplicationChildId(Long achildId);
}
