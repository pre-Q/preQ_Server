package kr.co.preq.domain.application.repository;

import java.util.Optional;

import kr.co.preq.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMemberIdOrderByCreatedAt(Long memberId);
	Optional<Application> findByIdAndMemberId(Long id, Long memberId);
}
