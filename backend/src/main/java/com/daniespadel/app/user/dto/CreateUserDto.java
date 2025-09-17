// CreateUserDto.java
package com.daniespadel.app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
    @NotBlank String name,
    @Email @NotBlank String email,
    @Size(min = 6, max = 100) String password,
    @NotBlank String role,   // "ADMIN" | "PLAYER"
    @Min(0) Integer level
) {}
