package com.effectivemobile.taskmanagement.repository;

import com.effectivemobile.taskmanagement.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
  Optional<List<Task>> findByPerformerAccountId(Long id);
  Optional<List<Task>> findByAuthorAccountId(Long id);
}
