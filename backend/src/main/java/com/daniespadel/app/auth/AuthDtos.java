package com.daniespadel.app.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

  public record LoginRequest(
      @Email String email,
      @NotBlank String password) {}

  public record LoginResponse(String token, UserSummary user) {}

  public record UserSummary(String id, String name, String email, String role, Integer level) {}
}
