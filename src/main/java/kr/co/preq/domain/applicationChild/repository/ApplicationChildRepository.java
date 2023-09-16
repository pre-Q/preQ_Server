package kr.co.preq.domain.applicationChild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;

import java.util.List;
import java.util.Optional;

public interface ApplicationChildRepository extends JpaRepository<ApplicationChild, Long> {
    Optional<ApplicationChild> findApplicationChildByIdAndApplicationIdAndMemberId(Long id, Long applicationId, Long memberId);
<<<<<<< HEAD
    List<ApplicationChild> findApplicationChildByApplicationIdAndMemberId(Long applicationId, Long memberId);
    List<ApplicationChild> findByApplicationId(Long applicationId);
=======
    List<ApplicationChild> findApplicationChildByApplicationIdAndMemberIdOrderByCreatedAt(Long applicationId, Long memberId);
>>>>>>> c4cdfb697c03969737ab9f28a2adcbc5f7e46b3e
}
