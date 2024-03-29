package kr.co.preq.domain.comment.repository;

import kr.co.preq.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>  {
    List<Comment> findByBoardIdOrderByCreatedAt(Long boardId);
}
