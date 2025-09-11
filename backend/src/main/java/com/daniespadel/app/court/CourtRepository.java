package com.daniespadel.app.court;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourtRepository extends JpaRepository<Court, UUID> {
  boolean existsByName(String name);
}
