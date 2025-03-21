package com.effectivemobile.taskmanagement.repository;

import com.effectivemobile.taskmanagement.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Override
  Optional<Comment> findById(Long id);

  Optional<List<Comment>> findByTaskId(Long id);
}
