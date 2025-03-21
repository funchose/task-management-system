package com.effectivemobile.taskmanagement.repository;

import com.effectivemobile.taskmanagement.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByUsername(String username);

  boolean existsByUsername(String username);

  Optional<Account> findByEmail(String email);
}
