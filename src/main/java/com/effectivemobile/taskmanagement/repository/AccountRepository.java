package com.effectivemobile.taskmanagement.repository;

import com.effectivemobile.taskmanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Long> {
}
