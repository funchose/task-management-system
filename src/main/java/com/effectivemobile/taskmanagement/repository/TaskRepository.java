package com.effectivemobile.taskmanagement.repository;

import com.effectivemobile.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository <Task, Long> {
}
