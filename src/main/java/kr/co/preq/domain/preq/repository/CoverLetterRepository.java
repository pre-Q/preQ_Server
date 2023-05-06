package kr.co.preq.domain.preq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.preq.entity.CoverLetter;

import java.util.List;
import java.util.Optional;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {
    Optional<CoverLetter> findCoverLetterById(Long cletterId);
    List<CoverLetter> findCoverLettersByMemberId(Long memberId);
}
