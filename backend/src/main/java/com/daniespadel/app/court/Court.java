package com.daniespadel.app.court;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "courts")
public class Court {
  @Id @GeneratedValue
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CourtStatus status = CourtStatus.OPEN;

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public CourtStatus getStatus() { return status; }
  public void setStatus(CourtStatus status) { this.status = status; }
}
