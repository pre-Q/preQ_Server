package kr.co.preq.domain.application.repository;

import kr.co.preq.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMemberId(Long memberId);
}
