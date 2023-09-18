package kr.co.preq.domain.applicationChild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.preq.domain.applicationChild.entity.ApplicationChild;

import java.util.List;
import java.util.Optional;

public interface ApplicationChildRepository extends JpaRepository<ApplicationChild, Long> {
    Optional<ApplicationChild> findApplicationChildByIdAndApplicationIdAndMemberId(Long id, Long applicationId, Long memberId);

    List<ApplicationChild> findByApplicationId(Long applicationId);

    List<ApplicationChild> findApplicationChildByApplicationIdAndMemberIdAndIsDeletedOrderByCreatedAt(Long applicationId, Long memberId, Boolean isDeleted);

    @Query(value = "WITH RECURSIVE find_division "
        + "as (\n"
        + "  select *, 1 as DEPTH\n"
        + "  from application_child\n"
        + "  where 1=1\n"
        + "  and id = ?1\n"
        + "\n"
        + "  union\n"
        + "  select d.*, fd.DEPTH+1\n"
        + "  from find_division fd\n"
        + "  INNER JOIN application_child d on fd.parent_id = d.id\n"
        + ")\n"
        + "select * from find_division order by DEPTH desc;", nativeQuery = true)
    List<ApplicationChild> findAllByApplicationChildIdOrderByParentIdAscNullsFirstCategoryIdAsc(Long applicationChildId);

    ApplicationChild findApplicationChildById(Long id);
}
