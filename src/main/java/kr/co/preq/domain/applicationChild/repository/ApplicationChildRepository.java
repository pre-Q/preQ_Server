package kr.co.preq.domain.applicationChild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;

import java.util.List;
import java.util.Optional;

public interface ApplicationChildRepository extends JpaRepository<ApplicationChild, Long> {
    Optional<ApplicationChild> findApplicationChildByIdAndApplicationIdAndMemberId(Long id, Long applicationId, Long memberId);

    List<ApplicationChild> findByApplicationId(Long applicationId);

    List<ApplicationChild> findApplicationChildByApplicationIdAndMemberIdAndIsDeletedOrderByCreatedAt(Long applicationId, Long memberId, Boolean isDeleted);
}
