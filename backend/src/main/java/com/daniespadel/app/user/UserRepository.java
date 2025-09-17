package com.daniespadel.app.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByEmailAndIdNot(String email, UUID id);

  Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
      String name, String email, Pageable pageable);
}
