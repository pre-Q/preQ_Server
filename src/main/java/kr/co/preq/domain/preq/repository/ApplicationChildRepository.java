package kr.co.preq.domain.preq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.preq.entity.ApplicationChild;

import java.util.List;
import java.util.Optional;

public interface ApplicationChildRepository extends JpaRepository<ApplicationChild, Long> {
    Optional<ApplicationChild> findCoverLetterById(Long cletterId);
    List<ApplicationChild> findCoverLettersByMemberId(Long memberId);
}